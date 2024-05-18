package com.kh.pheonix.Vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReservationListForReviewVo {

	private int reservationNo;
	private int movieScheduleNo;
	private String userId;
	private Date reservationDate;
	private String paymentStatus;
	private int totalPrice;
	private String paymentMethod;
	private int movieNo;
	private String endTime;
	private String startDate;
	private String movieTitle;
	private String movieScreenType;
	
}
