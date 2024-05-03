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
	
	private String lostImgLink;//이미지 링크를 위한 변수
}
