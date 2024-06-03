package com.kh.pheonix.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @Data @NoArgsConstructor @AllArgsConstructor
public class UserReviewVO {
	private int reviewNo;
	private String reviewContent;
	private String reviewWriter;
	private Date reviewDate;
	private int reservationNo;
	private int movieNo;
	
//	private String userId;
//	private String userNick;
	
	private String userImgLink;
	
	private boolean state;
	private int count;

}
