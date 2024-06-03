package com.kh.pheonix.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReserveStatsListVo {

	private int reserveStatsNo;
	private int reserveStatsMovie;
	private int reserveStatsAll;
	private float reserveStatsRate;
	private String reserveStatsDate;
	private int movieNo;
	private String movieTitle;
	private String movieOpenDate;
	
}
