package com.kh.pheonix.Vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class BookingListByDateVo {

	private int movieNo;
	private String cinemaName;
	private String startDate;
	
}
