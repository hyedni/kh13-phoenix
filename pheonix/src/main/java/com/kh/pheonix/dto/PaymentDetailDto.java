package com.kh.pheonix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PaymentDetailDto {
	private int paymentDetailNo; //고유번호
	private int paymentDetailProduct; //상품번호
	private int paymentDetailQty; //상품수량
	private String paymentDetailName; //구매이름(구매시점의 상품이름으로 변경되지 않음)
	private int paymentDetailPrice; //구매 가격(구매시점의 상품가격으로 변경되지 않음)
	private String paymentDetailStatus; //승인/취소
	private int paymentNo; //결제번호(연결)
	
	public int getTotalPrice() {
		return this.paymentDetailPrice * this.paymentDetailQty;
	}
}
