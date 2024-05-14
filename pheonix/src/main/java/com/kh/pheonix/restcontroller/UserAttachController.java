package com.kh.pheonix.restcontroller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.pheonix.dao.AttachDao;
import com.kh.pheonix.dao.UserDao;
import com.kh.pheonix.dto.AttachDto;
import com.kh.pheonix.dto.UserDto;
import com.kh.pheonix.service.AttachService;

import jakarta.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping("/user_attach")
public class UserAttachController {
	@Autowired
	private AttachService attachService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AttachDao attachDao;
	
	@PostMapping("/upload")
	public Integer upload(@RequestParam String userId, @RequestParam MultipartFile attach, HttpSession session) throws IllegalStateException, IOException {
		 // UserDao에서 loginDto 객체를 받아옴
		UserDto loginDto = userDao.selectOne(userId);

	    // UserDto 객체에서 로그인 ID를 얻어옵니다.
	    String loginId = loginDto.getUserId();
		
	    // 파일이 없으면 중지
		if(!attach.isEmpty()) {
			
			//기존 파일 삭제
			try {
				int attachNo = attachDao.findAttachNo(loginId);//파일번호찾고
				File dir = new File(System.getProperty("user.home"), "upload");
				File target = new File(dir, String.valueOf(attachNo));
				target.delete();//실제파일 삭제
				attachDao.delete(attachNo);//DB에서 삭제
			}
			catch(Exception e) {
				//e.printStackTrace();
			}//예외 발생 시 아무것도 안함(skip)
			
			//신규 파일 추가
			//- attach_seq 번호 생성
			//- 실물 파일을 저장
			//- DB에 insert
			//- member과 connect 처리
			int attachNo = attachDao.sequence();//시퀀스생성
			File dir = new File(System.getProperty("user.home"), "upload");
			File target = new File(dir, String.valueOf(attachNo));
			attach.transferTo(target);//실물파일저장
			
			AttachDto attachDto = new AttachDto();
			attachDto.setAttachNo(attachNo);
			attachDto.setAttachName(attach.getOriginalFilename());
			attachDto.setAttachType(attach.getContentType());
			attachDto.setAttachSize(attach.getSize());
			attachDao.insert(attachDto);//DB저장
			
			userDao.connect(loginId, attachNo);//연결처리
		}
		
	    // 파일 저장 및 해당 파일 번호 반환
	    return attachService.save(attach);
	}
	
	@PostMapping("/delete")
	public void delete(@RequestParam String userId, @RequestParam MultipartFile attach, HttpSession session) throws IllegalStateException, IOException {
		 // UserDao에서 loginDto 객체를 받아옴
		UserDto loginDto = userDao.selectOne(userId);

	    // UserDto 객체에서 로그인 ID를 얻어옵니다.
	    String loginId = loginDto.getUserId();
		
	    // 파일이 없으면 중지
		if(!attach.isEmpty()) {
			
			//기존 파일 삭제
			try {
				int attachNo = attachDao.findAttachNo(loginId);//파일번호찾고
				File dir = new File(System.getProperty("user.home"), "upload");
				File target = new File(dir, String.valueOf(attachNo));
				target.delete();//실제파일 삭제
				attachDao.delete(attachNo);//DB에서 삭제
			}
			catch(Exception e) {
				//e.printStackTrace();
			}//예외 발생 시 아무것도 안함(skip)
		}
	}
	
}
