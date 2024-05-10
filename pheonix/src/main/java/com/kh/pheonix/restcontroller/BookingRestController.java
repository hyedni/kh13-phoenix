package com.kh.pheonix.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.Vo.BookingListVo;
import com.kh.pheonix.Vo.MovieListVo;
import com.kh.pheonix.dao.BookingListDao;
import com.kh.pheonix.dto.SeatTypesDto;

@CrossOrigin
@RestController
@RequestMapping("/booking")
public class BookingRestController {
	
	@Autowired
	private BookingListDao bookingListDao;	
	
	
	//빈 문자열을 null로 처리하는 도구 설정 
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@GetMapping("/")
	public List<BookingListVo> listAll () {
		return bookingListDao.listAll();
	}
	
	@GetMapping("/movie")
	public List<MovieListVo> movieTitle () {
		return bookingListDao.movieTitle();
	}
	
	@GetMapping("/theater/{movieNo}")
	public List<String> listByMovie (@PathVariable int movieNo) {
		return bookingListDao.theaterList(movieNo);
	}
	
	@GetMapping("/cinema")
	public List<BookingListVo> listByCinema (@RequestBody BookingListVo vo) {
		return bookingListDao.listByCinema(vo.getMovieNo(), vo.getCinemaName());
	}
	
	@PostMapping("/date")
	public List<BookingListVo> listByDate (@RequestBody BookingListVo vo) {
		return bookingListDao.listByDate(vo.getMovieNo(), vo.getCinemaName(), vo.getStartDate());
	}
	
	//김민구 임시사용
	@GetMapping("/seatReservationStatus/{movieScheduleNo}")
	public List<SeatTypesDto> asd(@PathVariable int movieScheduleNo){
		List<SeatTypesDto> list = new ArrayList<>();
		int aaa = 100; //상영관번호 임시
		list = bookingListDao.seatTypes(121);
		
		return list;
	}

}



