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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kh.pheonix.Vo.BookingListVo;
import com.kh.pheonix.Vo.BookingTheaterVo;
import com.kh.pheonix.Vo.BookingVO;
import com.kh.pheonix.Vo.MovieListVo;
import com.kh.pheonix.dao.BookingListDao;
import com.kh.pheonix.dao.ReservationDao;
import com.kh.pheonix.dao.TheaterDao;
import com.kh.pheonix.dto.SeatReservationDto;
import com.kh.pheonix.dto.SeatTypesDto;
import com.kh.pheonix.service.BookingService;

@CrossOrigin
@RestController
@RequestMapping("/booking")
public class BookingRestController {

	@Autowired
	private BookingListDao bookingListDao;

	@Autowired
	private BookingService bookingService;

	@Autowired
	private ReservationDao reservationDao;
	
	@Autowired
	private TheaterDao theaterDao;
	


	// 빈 문자열을 null로 처리하는 도구 설정
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@GetMapping("/")
	public List<BookingListVo> listAll() {
		return bookingListDao.listAll();
	}

	@GetMapping("/movie")
	public List<MovieListVo> movieTitle() {
		return bookingListDao.movieTitle();
	}

	@GetMapping("/theater/{movieNo}")
	public List<String> listByMovie(@PathVariable int movieNo) {
		return bookingListDao.theaterList(movieNo);
	}


	@PostMapping("/count")
	public int count(@RequestBody BookingListVo vo) {
		return bookingListDao.count(vo.getMovieNo(), vo.getCinemaName());
	}
	
	@GetMapping("/detail/{scheduleNo}")
	public BookingListVo scheduleDetail (@PathVariable int scheduleNo) {
		return bookingListDao.scheduleDetail(scheduleNo);
	}
		

	@PostMapping("/date")
	public List<BookingListVo> listByDate(@RequestBody BookingListVo vo) {
		return bookingListDao.listByDate(vo.getMovieNo(), vo.getCinemaName(), vo.getStartDate());
	}

	@GetMapping("/seatReservationStatus/{movieScheduleNo}")
	public List<SeatTypesDto> asd(@PathVariable int movieScheduleNo) {
		
		int theaterNo = theaterDao.findtheaterNo(movieScheduleNo);
		
		List<SeatTypesDto> list = new ArrayList<>();
		list = bookingListDao.seatTypes(theaterNo,movieScheduleNo);

		return list;
	}

	@GetMapping("/theaterdistinct/{cinemaName}")
	public List<BookingTheaterVo> theaterDistinct(@PathVariable String cinemaName) {
		return bookingListDao.theaterDistinct(cinemaName);

	}

	@PostMapping("/bookingAdd")
	public String bookingAdd(@RequestBody BookingVO bookingVo) throws JsonProcessingException {
		return bookingService.bookingAdd(bookingVo);
	}
	
	
	
	@PostMapping("/price")
	public int bookingPrice(@RequestBody BookingVO bookingVo) {
		int total = 0;
		List<SeatReservationDto> seatReservation = bookingVo.getSeatReservationDto();
		int movieScheduleNo = bookingVo.getBookingStatusVO().getMovieScheduleNo();
		
		for (SeatReservationDto dto : seatReservation) {
			int seatTypesNo = dto.getSeatTypesNo();
			String memberType = dto.getMemberType();
			int price = reservationDao.ticketPriceCalculator(seatTypesNo, movieScheduleNo, memberType);
			total += price;
		}
		//asa
		return total;
	}

}
