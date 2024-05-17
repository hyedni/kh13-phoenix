package com.kh.pheonix.Vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class MovieRankingVo {

	private int movieNo;
	private String movieTitle;
	private String movieOpenDate;
	private String movieAge;
	private String movieOn;
	private float reserveStatsRate;
	private String movieImgLink;
	
}
