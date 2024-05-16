package com.kh.pheonix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.pheonix.Vo.BookingVO;
import com.kh.pheonix.dao.ReservationDao;
import com.kh.pheonix.dao.TheaterDao;
import com.kh.pheonix.dto.ReservationDto;
import com.kh.pheonix.dto.SeatReservationDto;
import com.kh.pheonix.error.CustomDatabaseException;

@Service
public class BookingService {
	
	@Autowired
	private ReservationDao reservationDao;
	
	@Autowired
	private TheaterDao theaterDao;

	
	@Transactional
	public String bookingAdd(BookingVO bookingVo) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = "";
		jsonString = objectMapper.writeValueAsString(bookingVo);
		System.out.println("데이터 확인용----" + (jsonString));
		// 위 테스트 용 아래부터 시작

		try {
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

			System.out.println("의심병 /" + seatTypesNo + "//" + movieScheduleNo + "//" + memberType);
			int price = reservationDao.ticketPriceCalculator(seatTypesNo, movieScheduleNo, memberType);

			System.out.println("의심병::::" + price);
			total += price;
		}
		System.out.println("의심 2-3::::" + total);

		// 다하고 예매 등록
		reservationDto.setTotalPrice(total);
		
		reservationDao.insert(reservationDto);
		System.out.println("의심 3");

		// 여기서부터좌석예매 반복문

		for (SeatReservationDto dto : seatReservation) {
			dto.setMovieScheduleNo(movieScheduleNo);
			dto.setReservationNo(reservationNo);

			reservationDao.insertSeat(dto);
			theaterDao.updateRemainingSeats(movieScheduleNo);

		}
		System.out.println("성공성공성공성공");

		return "주님할렐루야할렐루야";
		} catch(DuplicateKeyException e) {
			 System.err.println("DuplicateKeyException 발생: " + e.getMessage());
	            throw new CustomDatabaseException("이미예약된 좌석입니다. 다시선택해주세요.", e);
			
		}catch(Exception e) {
			System.err.println("일반적인 오류 발생: " + e.getMessage());
            throw new CustomDatabaseException("일반적인 데이터베이스 오류가 발생했습니다.", e);
		}
	}

}
