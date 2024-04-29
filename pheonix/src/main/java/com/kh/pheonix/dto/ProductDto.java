package com.kh.pheonix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ProductDto {
	private int productNo; //상품번호
	private String productName; //상품명
	private String prodcutType; //상품종류
	private int prodcutPrice; //상품가격
	private String productOrigin; //원산지
}
