package com.kh.pheonix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class MovieScheduleDto {
	
	private int movieScheduleNo;
	private Integer movieNo;
	private Integer theaterNo;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	private Integer remainingSeats;
	private String movieScheduleDateDisc;
	private String movieScheduleTimeDisc;
	
}
