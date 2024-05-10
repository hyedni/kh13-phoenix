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
	private Integer productPrice; //상품가격
	private String productOrigin; //원산지
	private Integer productDiscount; //할인율
//	private MultipartFile attach;
	
	private String productImgLink; //이미지 링크를 위한 변수
}
