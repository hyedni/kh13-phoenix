package com.kh.pheonix.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CartDto {
	private String cartUserId;//회원 아이디 FK
	private int cartProductNo;//상품 번호 FK
	private int cartQty;//담은 수량
	private Date cartDate;//담은 날짜
}
