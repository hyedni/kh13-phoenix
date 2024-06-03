package com.kh.pheonix.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class BookingListVO {

	private String movieTitle;
	private int movieNo;
	private String cinemaName;
	private String cinemaRegion;
	private String theaterName;
	private int theaterTotalSeats;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	private int remainingSeats;
	private String movieScheduleDateDisc;
	private String movieScheduleTimeDisc;
	private int movieScheduleNo;
	
}
