package com.kh.pheonix.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.dao.TheaterDao;
import com.kh.pheonix.dto.TheaterDto;

@CrossOrigin
@RestController
@RequestMapping("/theater")
public class TheaterRestController {
	
	@Autowired
	private TheaterDao theaterDao;
	
	//영화관 정보로 조회한 상영관들
	@GetMapping("/{cinemaNo}")
	public List<TheaterDto> listByCinemaNo (@PathVariable int cinemaNo) {
		System.out.println(cinemaNo);
		return theaterDao.find(cinemaNo);
	}

}
