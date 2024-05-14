package com.kh.pheonix.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.Vo.TheaterPayLoadVo;
import com.kh.pheonix.dao.SeatTypesDao;
import com.kh.pheonix.dao.TheaterDao;
import com.kh.pheonix.dto.SeatTypesDto;
import com.kh.pheonix.dto.TheaterDto;

@CrossOrigin
@RestController
@RequestMapping("/theater")
public class TheaterRestController {
	
	@Autowired
	private TheaterDao theaterDao;
	
	@Autowired
	private SeatTypesDao seatTypesDao;
	
	//영화관 정보로 조회한 상영관들
	@GetMapping("/{cinemaNo}")
	public List<TheaterDto> listByCinemaNo (@PathVariable int cinemaNo) {
		System.out.println(cinemaNo);
		return theaterDao.find(cinemaNo);
	}
	
	@PostMapping("/")
	public void addTheater (@RequestBody TheaterPayLoadVo payload){
		 System.out.println("의심병1: " + payload);
		 
		int theaterNo = theaterDao.sequence();
		System.out.println("의심병2"+theaterNo); 
		
		TheaterDto theater = payload.getTheater();
		System.out.println("의심병3"+theater); 
		theater.setTheaterNo(theaterNo);
		System.out.println("의심병4"+theater); 
		theaterDao.insert(theater);
		System.out.println("의심병5"+theater); 
		
		List<SeatTypesDto> seats = payload.getSeats();
		System.out.println("의심병6"+seats); 
		for (SeatTypesDto seatTypesDto : seats) {
			seatTypesDto.setTheaterNo(theater.getTheaterNo());
			seatTypesDao.insert(seatTypesDto);
		}
		
		 
		 int totalSeats =theaterDao.totalSeats(theaterNo); 
		 System.out.println("의심병7"+totalSeats);
		 theaterDao.totalSeatsUpdate(theaterNo, totalSeats);
		 System.out.println("의심병8 제발제발");
		 
		
	}

}
