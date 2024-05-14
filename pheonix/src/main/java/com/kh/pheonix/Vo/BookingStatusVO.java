package com.kh.pheonix.Vo;

import com.kh.pheonix.dto.ChatbotDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class BookingStatusVO {

	private int movieScheduleNo;
	private String userId;
	private String paymentMethod;
}
