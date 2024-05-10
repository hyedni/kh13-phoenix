package com.kh.pheonix.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReviewDto {
	private int reviewNo;
	private String reviewContent;
	private String reviewWriter;
	private Date reviewDate;
	private int reviewUserBookingNo;
	private int reviewReputation;
}
