package com.kh.pheonix.Vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//준비요청에 대한 응답(카카오페이가 주는 정보 담기)
@JsonIgnoreProperties(ignoreUnknown = true)//혹시 없는 항목은 넘어가줘...
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)//카멜케이스로 바꿔줘..
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class KakaoPayReadyResponseVO {
	private String tid;//결제 고유번호(20자)
	private String nextRedirectMobileUrl;//모바일 웹용 결제페이지 주소
	private String nextRedirectPcUrl;//PC용 결제페이지 주소
	private String createdAt;//결제 준비 요청 시각
}
