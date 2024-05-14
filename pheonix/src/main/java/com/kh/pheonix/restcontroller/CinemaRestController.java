package com.kh.pheonix.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.dao.CinemaDao;
import com.kh.pheonix.dto.CinemaDto;
import com.kh.pheonix.dto.MovieDto;


@CrossOrigin
@RestController
@RequestMapping("/cinema")
public class CinemaRestController {

	@Autowired
	private CinemaDao cinemaDao;
	
	
	@GetMapping("/")
	public List<CinemaDto> list () {
		return cinemaDao.list();
	}
	
	//영화관등록
	@PostMapping("/")
	public CinemaDto insert (@RequestBody CinemaDto cinemaDto) {
		int seq = cinemaDao.sequence();
		cinemaDto.setCinemaNo(seq);
		cinemaDao.insert(cinemaDto);
		return cinemaDao.findByNo(seq);
	}
		
	//영화관 상세조회
	@GetMapping("/{cinemaNo}")
	public ResponseEntity<CinemaDto> find (@PathVariable int cinemaNo) {
		CinemaDto cinemaDto = cinemaDao.findByNo(cinemaNo);
		if (cinemaDto == null) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.status(200).body(cinemaDto);
	}
	
	//수정
	@PatchMapping("/")
	public ResponseEntity<?> edit (@RequestBody CinemaDto cinemaDto) {
		boolean result = cinemaDao.edit(cinemaDto);
		if (result == false) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
	
	//삭제
	@DeleteMapping("/{cinemaNo}")
	public ResponseEntity<?> delete (@PathVariable int cinemaNo) {
		boolean result = cinemaDao.delete(cinemaNo);
		if (!result) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
}















