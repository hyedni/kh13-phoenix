package com.kh.pheonix.restcontroller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.pheonix.dao.LostDao;
import com.kh.pheonix.dto.LostDto;
import com.kh.pheonix.service.AttachService;
import com.kh.pheonix.service.ImageService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/lost")
public class LostRestController {

	@Autowired
	private AttachService attachService;
	
	@Autowired
	private LostDao lostDao;
	
	@Autowired
	private ImageService imageService;
	
	@GetMapping("/")//조회
	public List<LostDto> list() {
		List<LostDto> list = lostDao.selectList();
		List<LostDto> imageSetUpList = imageService.lostPhotoUrlSetUp(list);//사진 주소 설정
		return imageSetUpList;
	}
	

	
	@PostMapping("/") // 등록
	public LostDto insert(@ModelAttribute LostDto lostDto, @RequestParam(value = "attach", required = false) MultipartFile attach)
	        
			throws IllegalStateException, IOException {
	    try {
	        int sequence = lostDao.sequence();
	        lostDto.setLostNo(sequence);
	        System.out.println(lostDto);
	        lostDao.insert(lostDto);
	        
	        if(attach != null && !attach.isEmpty()) {
	            int attachNo = attachService.save(attach);
	            lostDao.connect(lostDto.getLostNo(), attachNo);
	        }
	        System.out.println("성공이용ㅋ");
	        return lostDao.selectOne(sequence);
	    } catch(Exception e) {
	        System.out.println("실패용ㅋ " + e.getMessage()); 
	        throw e; 
	    }
	}


	
	@PutMapping("/")//전체수정
	public boolean edit(@RequestBody LostDto lostDto) {
		boolean result = lostDao.update(lostDto);
		return result;
	}
	
	@DeleteMapping("/{lostNo}")//삭제
	public boolean delete(@PathVariable int lostNo) {
		return lostDao.delete(lostNo);
	}
	
	@GetMapping("/{lostNo}")//상세조회
	public LostDto find(@PathVariable int lostNo) {
		LostDto lostDto = lostDao.selectOne(lostNo);
		return lostDto;
	}
	
}

