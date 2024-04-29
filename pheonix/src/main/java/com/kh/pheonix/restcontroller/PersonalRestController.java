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

import com.kh.pheonix.dao.PersonalDao;
import com.kh.pheonix.dto.PersonalDto;

@CrossOrigin
@RestController
@RequestMapping("/personal")
public class PersonalRestController {

	@Autowired
	private PersonalDao personalDao;

	@GetMapping("/")//조회
	public List<PersonalDto> list() {
		return personalDao.selectList();
	}
	
	@PostMapping("/")//등록
	public void insert(@RequestBody PersonalDto personalDto) {
		personalDao.insert(personalDto);
	}
	
	@PutMapping("/")//전체수정
	public boolean edit(@RequestBody PersonalDto personalDto) {
		boolean result = personalDao.update(personalDto);
		return result;
	}
	
	@DeleteMapping("/{personalNo}")//삭제
	public boolean delete(@PathVariable int personalNo) {
		return personalDao.delete(personalNo);
	}
	
	@GetMapping("/{personalNo}")//상세조회
	public PersonalDto find(@PathVariable int personalNo) {
		PersonalDto personalDto = personalDao.selectOne(personalNo);
		return personalDto;
	}
}