package com.kh.pheonix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CinemaDto {

	private int cinemaNo;
	private String cinemaName;
	private Integer cinemaTotalTheater;
	private String cinemaRegion;
	private Integer cinemaPost;
	private String cinemaAddress1;
	private String cinemaAddress2;
	private String cinemaManager;
	private String cinemaManagerCall;
	private String cinemaCall;
	
}
