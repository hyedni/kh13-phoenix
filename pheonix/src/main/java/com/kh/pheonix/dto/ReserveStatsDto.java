package com.kh.pheonix.dto;

import java.sql.Date;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReserveStatsDto {

	private int reserveStatsNo;
	private int movieNo;
	private int reserveStatsMovie;
	private int reserveStatsAll;
	private float reserveStatsRate;
	private Date reserveStatsDate;
	
}
