package com.kh.pheonix.Vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class KakaoPayCancelRequestVO {
	private String tid;
	private int cancelAmount;
}

