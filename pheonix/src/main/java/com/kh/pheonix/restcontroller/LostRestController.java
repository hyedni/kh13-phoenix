package com.kh.pheonix.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.pheonix.dao.LostDao;
import com.kh.pheonix.dto.LostDto;
import com.kh.pheonix.service.AttachService;

import io.jsonwebtoken.io.IOException;
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
	
	@GetMapping("/")//조회
	public List<LostDto> list() {
		return lostDao.selectList();
	}
	
	@PostMapping("/") // 등록
	public LostDto insert(@RequestParam LostDto lostDto, @RequestParam MultipartFile attach) 
	        throws IllegalStateException, IOException, java.io.IOException{
	    int sequence = lostDao.sequence();
	    lostDto.setLostNo(sequence);
	    lostDao.insert(lostDto);
	    
	    if(!attach.isEmpty()) {
	        int attachNo = attachService.save(attach);
	        lostDao.connect(lostDto.getLostNo(), attachNo);
	    }
	    return lostDao.find(sequence);
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
