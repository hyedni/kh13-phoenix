package com.kh.pheonix.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PaymentDto {
	private int paymentNo; //고유번호
	private Date paymentTime; //구매일시, 현재시각으로 설정
	private String paymentName; //구매명, 카카오페이 결제 상품명
	private Integer paymentTotal; //결제금액, 카카오페이 결제 총 금액
	private Integer paymentRemain; //잔여금액, 취소 후 남은 금액
	private String memberId; //구매자
	private String paymentTid; //결제고유번호(== 거래번호)
	
	private int no;
	private int qty;
	private int usedPoint;
}
