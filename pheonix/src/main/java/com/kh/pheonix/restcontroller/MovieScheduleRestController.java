package com.kh.pheonix.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.Vo.MovieScheduleVo;
import com.kh.pheonix.dao.MovieScheduleDao;
import com.kh.pheonix.dto.MovieDto;
import com.kh.pheonix.dto.MovieScheduleDto;

@CrossOrigin
@RestController
@RequestMapping("/movieSchedule")
public class MovieScheduleRestController {

	@Autowired
	private MovieScheduleDao movieScheduleDao;
	
	@PostMapping("/")
	public List<MovieScheduleVo> list (@RequestBody(required = false) MovieScheduleVo vo) {
		return movieScheduleDao.list(vo);
	}
	
	@GetMapping("/movieList")
	public List<Map<String, Object>> movieList () {
		return movieScheduleDao.movieList();
	}
	
	@GetMapping("/cinemaList")
	public List<Map<String, Object>> cinemaList () {
		return movieScheduleDao.cinemaList();
	}
	
	@GetMapping("/theaterList/{cinemaNo}")
	public List<Map<String, Object>> theaterList (@PathVariable int cinemaNo) {
		return movieScheduleDao.theaterList(cinemaNo);
	}
	
	@GetMapping("/seats/{theaterNo}")
	public int seats (@PathVariable int theaterNo) {
		return movieScheduleDao.seats(theaterNo);
	}
	
	@PostMapping("/insert")
	public void insert (@RequestBody MovieScheduleDto movieScheduleDto) {
		int seq = movieScheduleDao.seq();
		movieScheduleDto.setMovieScheduleNo(seq);
		movieScheduleDao.insert(movieScheduleDto);
	}
	
	@GetMapping("/runningTime/{movieNo}")
	public int getRunningTime (@PathVariable int movieNo) {
		return movieScheduleDao.getRunningTime(movieNo);
	}
	
	@DeleteMapping("/{movieScheduleNo}")
	public ResponseEntity<?> delete(@PathVariable int movieScheduleNo) {
		boolean result = movieScheduleDao.delete(movieScheduleNo);
		if (!result) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
	
	// 수정
	@PatchMapping("/")
	public ResponseEntity<?> edit(@RequestBody MovieScheduleDto movieScheduleDto) {
		boolean result = movieScheduleDao.edit(movieScheduleDto);
		if (result == false) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{movieScheduleNo}")
	public MovieScheduleVo scheduleFind (@PathVariable int movieScheduleNo) {
		return movieScheduleDao.scheduleFind(movieScheduleNo);
	}
	
}





