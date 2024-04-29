package com.kh.pheonix.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PurchaseDto {
	private int purchaseNo;//구매내역번호 PK
	private String purchaseName;//상품명
	private int purchaseQty;//수량
	private int purchasePrice;//상품가격(낱개 가격)
	private int purchaseTotalPrice;//총 가격(qty*price)
	private String purchaseStatus;//구매 상태
	private String purchasePayment;//지불방법
	private int purchaseUsedPoint;//사용한 포인트
	private int purchaseCashPay;//지불한 현금
	private Date purchaseExpire;//사용기한
}
