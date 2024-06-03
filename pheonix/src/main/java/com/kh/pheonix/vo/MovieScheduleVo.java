package com.kh.pheonix.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class MovieScheduleVo {

	private int movieNo;
	private String movieTitle;
	private int movieRunningTime;
	private String movieOpenDate;
	private String movieCloseDate;
	private String movieAge;
	private String movieOn;
	private String movieScreenType;
	private int movieScheduleNo;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	private int remainingSeats;
	private String movieScheduleDateDisc;
	private String movieScheduleTimeDisc;
	private String cinemaName;
	private int cinemaNo;
	private int theaterNo;
	private String theaterName;
	private int theaterTotalSeats;
	private int page = 1;
	private int size = 10;
	private int beginRow;
	private int endRow;
	
	
	public int getBeginRow() {
		return page * size - (size - 1);
	}
	
	public int getEndRow() {
		return page * size;
	}
}
