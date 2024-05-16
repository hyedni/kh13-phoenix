package com.kh.pheonix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SeatTypesDto {

	private int theaterNo;
	private int seatTypesNo;
	private int cellX;
	private int cellY;
	private Integer seatX;
	private String seatY;
	private String seatType;
	private String cellActive; 
	private String seatActive;
	private String reservationStatus;
	
}
