package com.kh.pheonix.kakaoPayVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class FlashOneInfoRequestVO {
	private String cartPartnerOrderId;
	private String cartPartnerUserId;
	private String cartTid;
	private String pgToken;
	private PurchaseVO purchaseList;
}
