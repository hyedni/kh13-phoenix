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
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.dao.WriteDao;
import com.kh.pheonix.dto.WriteDto;

@CrossOrigin
@RestController
@RequestMapping("/write")
public class WriteRestController {

	@Autowired
	private WriteDao writeDao;
	
	@GetMapping("/")//조회
	public List<WriteDto> list() {
		return writeDao.selectList();
	}
	
	@PostMapping("/")//등록
	public void insert(@RequestBody WriteDto writeDto) {
		writeDao.insert(writeDto);
	}
	
	@PutMapping("/")//전체수정
	public boolean edit(@RequestBody WriteDto writeDto) {
		boolean result = writeDao.update(writeDto);
		return result;
	}
	
	@DeleteMapping("/{writeNo}")//삭제
	public boolean delete(@PathVariable int writeNo) {
		return writeDao.delete(writeNo);
	}
	
	@GetMapping("/{writeNo}")//상세조회
	public WriteDto find(@PathVariable int writeNo) {
		WriteDto writeDto = writeDao.selectOne(writeNo);
		return writeDto;
	}
	
	@PostMapping("/reply") // 답글 등록
	public void insertReply(@RequestBody WriteDto writeDto) {
	    writeDao.insertReply(writeDto);
	}

	}

