package com.kh.pheonix.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.Vo.UserLoginVO;
import com.kh.pheonix.dao.ReservationDao;
import com.kh.pheonix.dto.ReservationDetailDto;
import com.kh.pheonix.dto.ReservationDto;
import com.kh.pheonix.service.JwtService;

@CrossOrigin
@RestController
@RequestMapping("/reservation")
public class ReservationRestController {
	
	@Autowired
	private ReservationDao reservationDao;
	@Autowired
	private JwtService jwtService;
	
	@GetMapping("/list/{userId}")
	public List<ReservationDetailDto> list(@PathVariable String userId){
		return reservationDao.selectList(userId);
	}
	
	@GetMapping("/{reservationNo}")
	public ReservationDetailDto searchOne(@PathVariable int reservationNo,
			@RequestHeader("Authorization") String refreshToken) {
		UserLoginVO loginVO = jwtService.parse(refreshToken);
		return reservationDao.selectOne(loginVO.getUserId(), reservationNo);
	}
	
	
}
