package com.kh.pheonix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class GiftDto {
	private int giftNo;//선물번호 PK
	private String giftUserId; //회원아이디 FK
	private int giftPurchaseNo;//구매 번호 FK
	private String giftTo;//받는 사람
	private String giftEmail;//받는 사람 이메일
	private String giftContent;//받는 사람에게 보낼 내용
}
