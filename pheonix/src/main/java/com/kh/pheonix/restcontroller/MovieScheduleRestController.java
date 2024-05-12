package com.kh.pheonix.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.Vo.MovieScheduleVo;
import com.kh.pheonix.dao.MovieScheduleDao;

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
}




