package com.kh.pheonix.kakaoPayVO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//프론트에 넘겨줘야하는 정보들....
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class FlashInfoRequestVO {
	private String cartPartnerOrderId;
	private String cartPartnerUserId;
	private String cartTid;
	private String pgToken;
	private List<PurchaseVO> purchaseList;
}
