package com.kh.pheonix.restcontroller;

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

import com.kh.pheonix.Vo.BookingListByDateVo;
import com.kh.pheonix.Vo.BookingListVo;
import com.kh.pheonix.Vo.BookingTheaterVo;
import com.kh.pheonix.Vo.MovieListVo;
import com.kh.pheonix.dao.BookingListDao;

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
	
	@PostMapping("/count")
	public int count (@RequestBody BookingListVo vo) {
		return bookingListDao.count(vo.getMovieNo(), vo.getCinemaName());
	}
	
	@PostMapping("/date")
	public List<BookingListVo> listByDate (@RequestBody BookingListVo vo) {
		return bookingListDao.listByDate(vo.getMovieNo(), vo.getCinemaName(), vo.getStartDate());
	}
	
	@GetMapping("/theaterdistinct/{cinemaName}" )
	public List<BookingTheaterVo> theaterDistinct (@PathVariable String cinemaName) {
		return bookingListDao.theaterDistinct(cinemaName);
	}

}



