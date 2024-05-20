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
		return theaterDao.find(cinemaNo);
	}
	
	@PostMapping("/")
	public void addTheater (@RequestBody TheaterPayLoadVo payload){
		int theaterNo = theaterDao.sequence();
		
		TheaterDto theater = payload.getTheater();
		theater.setTheaterNo(theaterNo);
		theaterDao.insert(theater);
		
		List<SeatTypesDto> seats = payload.getSeats();
		for (SeatTypesDto seatTypesDto : seats) {
			seatTypesDto.setTheaterNo(theater.getTheaterNo());
			seatTypesDao.insert(seatTypesDto);
		}
		 
		 int totalSeats =theaterDao.totalSeats(theaterNo); 
		 theaterDao.totalSeatsUpdate(theaterNo, totalSeats);
	}

}
