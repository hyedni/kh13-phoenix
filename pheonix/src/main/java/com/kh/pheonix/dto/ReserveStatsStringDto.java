package com.kh.pheonix.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReserveStatsStringDto {

	private int reserveStatsNo;
	private int movieNo;
	private int reserveStatsMovie;
	private int reserveStatsAll;
	private float reserveStatsRate;
	private String reserveStatsDate;
}
