package com.kh.pheonix.Vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CartProductVO {
	private String cartUserId;//회원 아이디 FK
	private int cartProductNo;//상품 번호 FK
	private int cartQty;//담은 수량
	private Date cartDate;//담은 날짜
	
	private int productNo; //상품번호
	private String productName; //상품명
	private String productType; //상품종류
	private String productContent; //상품 내용
	private Integer productPrice; //상품가격
	private String productOrigin; //원산지
	private Integer productDiscount; //할인율
	
	private String productImgLink; //이미지 링크를 위한 변수
}
