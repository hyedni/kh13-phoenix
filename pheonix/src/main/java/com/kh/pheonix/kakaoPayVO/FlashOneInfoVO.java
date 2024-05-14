package com.kh.pheonix.kakaoPayVO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class FlashOneInfoVO {
	private String nextRedirectPcUrl;//PC용 결제페이지 주소
	private String partnerOrderId;
	private String partnerUserId;
	private String tid;
	//private PurchaseListVO vo;
	private PurchaseVO vo;
}
