package com.kh.pheonix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SeatReservationDto {
	
	private int seatReservationNo;
	private int seatTypesNo;
	private int reservationNo;
	private String memberType;
	private String reservationStatus;
	private int movieScheduleNo;

}
