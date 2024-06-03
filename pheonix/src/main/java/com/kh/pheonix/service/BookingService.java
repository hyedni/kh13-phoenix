package com.kh.pheonix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.pheonix.dao.ReservationDao;
import com.kh.pheonix.dao.TheaterDao;
import com.kh.pheonix.dto.ReservationDto;
import com.kh.pheonix.dto.SeatReservationDto;
import com.kh.pheonix.error.CustomDatabaseException;
import com.kh.pheonix.vo.BookingVO;

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
		// 위 테스트 용 아래부터 시작

		try {
			// 예매시퀀스뽑고 세팅
			int reservationNo = reservationDao.sequence();
			ReservationDto reservationDto = new ReservationDto();
			reservationDto.setReservationNo(reservationNo);
			// 상영번호 받은거에서 뽑고 세팅
			int movieScheduleNo = bookingVo.getBookingStatusVO().getMovieScheduleNo();
			reservationDto.setMovieScheduleNo(movieScheduleNo);
			reservationDto.setUserId(bookingVo.getBookingStatusVO().getUserId());
			reservationDto.setPaymentMethod(bookingVo.getBookingStatusVO().getPaymentMethod());

			// 예매 총가격 해야함
			List<SeatReservationDto> seatReservation = bookingVo.getSeatReservationDto();
			int total = 0;

			for (SeatReservationDto dto : seatReservation) {
				int seatTypesNo = dto.getSeatTypesNo();
				String memberType = dto.getMemberType();

				int price = reservationDao.ticketPriceCalculator(seatTypesNo, movieScheduleNo, memberType);

				total += price;
			}

			// 다하고 예매 등록
			reservationDto.setTotalPrice(total);
			reservationDao.insert(reservationDto);

			// 여기서부터좌석예매 반복문

			for (SeatReservationDto dto : seatReservation) {
				dto.setMovieScheduleNo(movieScheduleNo);
				dto.setReservationNo(reservationNo);

				reservationDao.insertSeat(dto);
				theaterDao.updateRemainingSeats(movieScheduleNo);

			}
			return "주님할렐루야할렐루야";
		} catch (DuplicateKeyException e) {
			throw new CustomDatabaseException("이미예약된 좌석입니다. 다시선택해주세요.", e);

		} catch (Exception e) {
			throw new CustomDatabaseException("일반적인 데이터베이스 오류가 발생했습니다.", e);
		}
	}

	

}
