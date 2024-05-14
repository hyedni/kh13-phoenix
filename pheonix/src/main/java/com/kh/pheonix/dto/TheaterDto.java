package com.kh.pheonix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class TheaterDto {

	private int theaterNo;
	private int cinemaNo;
	private String theaterName;
	private Integer theaterTotalSeats;
	//a
}
