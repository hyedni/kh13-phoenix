package com.kh.pheonix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class MovieDto {

	private int movieNo;
	private String movieTitle;
	private String movieGenre;
	private Integer movieRunningTime;
	private String movieYear;
	private String movieOpenDate;
	private String movieCloseDate;
	private String movieAge;
	private String movieOrigin;
	private String movieOn;
	private String movieSummary;
	private String movieTranslation;
	private String movieScreenType;
	private String movieDirector;
	private String movieActor;
	
}
