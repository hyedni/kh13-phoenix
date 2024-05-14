package com.kh.pheonix.Vo;

import java.util.List;

import com.kh.pheonix.dto.SeatReservationDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class BookingVO {

	
	private BookingStatusVO bookingStatusVO;
	private List<SeatReservationDto> seatReservationDto;
	
}
