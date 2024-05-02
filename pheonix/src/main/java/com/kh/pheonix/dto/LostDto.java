package com.kh.pheonix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class LostDto {
	private int lostNo;
	private String lostTitle;
	private String lostContent;
}
