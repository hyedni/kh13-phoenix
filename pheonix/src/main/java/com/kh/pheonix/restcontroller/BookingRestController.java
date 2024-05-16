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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.pheonix.Vo.BookingListVo;
import com.kh.pheonix.Vo.BookingTheaterVo;
import com.kh.pheonix.Vo.BookingVO;
import com.kh.pheonix.Vo.MovieListVo;
import com.kh.pheonix.dao.BookingListDao;
import com.kh.pheonix.dao.ReservationDao;
import com.kh.pheonix.dto.ReservationDto;
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
		List<SeatTypesDto> list = new ArrayList<>();
		// int aaa = 100; //상영관번호 임시
		list = bookingListDao.seatTypes(121);

		return list;
	}

	@GetMapping("/theaterdistinct/{cinemaName}")
	public List<BookingTheaterVo> theaterDistinct(@PathVariable String cinemaName) {
		return bookingListDao.theaterDistinct(cinemaName);

	}

	@PostMapping("/bookingAdd")
	public String bookingAdd(@RequestBody BookingVO bookingVo) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = "";
		jsonString = objectMapper.writeValueAsString(bookingVo);
		System.out.println("데이터 확인용----" + (jsonString));
		// 위 테스트 용 아래부터 시작
		
		// 예매시퀀스뽑고 세팅
		int reservationNo = reservationDao.sequence();
		ReservationDto reservationDto = new ReservationDto();
		reservationDto.setReservationNo(reservationNo);
		System.out.println("의심 1");
		// 상영번호 받은거에서 뽑고 세팅
		int movieScheduleNo = bookingVo.getBookingStatusVO().getMovieScheduleNo();
		System.out.println("의심 1-1");
		reservationDto.setMovieScheduleNo(movieScheduleNo);
		System.out.println("의심 1-2");
		reservationDto.setUserId(bookingVo.getBookingStatusVO().getUserId());
		System.out.println("의심 1-3");
		reservationDto.setPaymentMethod(bookingVo.getBookingStatusVO().getPaymentMethod());
		System.out.println("의심 2");

		// 예매 총가격 해야함
		List<SeatReservationDto> seatReservation = bookingVo.getSeatReservationDto();
		System.out.println("의심 2-1");
		int total = 0;
		System.out.println("의심 2-2");
		
		for (SeatReservationDto dto : seatReservation) {
			int seatTypesNo = dto.getSeatTypesNo();
			String memberType = dto.getMemberType();

			System.out.println("의심병 /"+seatTypesNo + "//" +movieScheduleNo +"//" + memberType);
			int price = reservationDao.ticketPriceCalculator(seatTypesNo, movieScheduleNo, memberType);
			
			
			System.out.println("의심병::::"+price);
			total += price;
		}
		System.out.println("의심 2-3::::"+total);
		
		
		// 다하고 예매 등록
		reservationDao.insert(reservationDto);
		System.out.println("의심 3");

		// 여기서부터좌석예매 반복문

		for (SeatReservationDto dto : seatReservation) {
			dto.setMovieScheduleNo(movieScheduleNo);
			dto.setReservationNo(reservationNo);

			reservationDao.insertSeat(dto);
			
		}
		System.out.println("성공성공성공성공");

		return "주님할렐루야할렐루야";
	}
	

}
