package com.kh.pheonix.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class BookingTheaterVo {

	private int theaterNo;
	private String theaterName;
	
}
