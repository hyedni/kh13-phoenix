package com.kh.pheonix.kakaoPayVO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PurchasePointVO {
	private int usedPoint;//사용한 포인트
	private List<PurchaseVO> vo;
}
