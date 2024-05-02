package com.kh.pheonix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ProductDto {
	private int productNo; //상품번호
	private String productName; //상품명
	private String productType; //상품종류
	private String productContent; //상품 내용
	private int productPrice; //상품가격
	private String productOrigin; //원산지
	private int productDiscount; //할인율
}
