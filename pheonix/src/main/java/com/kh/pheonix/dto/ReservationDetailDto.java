package com.kh.pheonix.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReservationDetailDto {

	private int reservationNo;
	private String movieTitle;
	private int movieScheduleNo;
	private String movieScreenType;
	private int totalPrice;
	private String paymentStatus;
}
