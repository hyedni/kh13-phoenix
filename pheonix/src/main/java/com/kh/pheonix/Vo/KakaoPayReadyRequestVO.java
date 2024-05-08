package com.kh.pheonix.Vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//카카오페이 준비 요청을 위한 VO (사용자)
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class KakaoPayReadyRequestVO {
	private String partnerOrderId;//가맹점 주문번호 최대 100자
	private String partnerUserId;//가맹점 회원id, 최대 100자
	private String itemName;//상품명 최대 100자
	private int totalAmount;//상품 총액
}
