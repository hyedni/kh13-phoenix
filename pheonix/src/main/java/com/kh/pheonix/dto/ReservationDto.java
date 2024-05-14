package com.kh.pheonix.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReservationDto {
	private int reservationNo;
	private int movieScheduleNo;
	private String userId;
	private Date reservationDate;
	private String paymentStatus;
	private int totalPrice;
	private String paymentMethod;

}
