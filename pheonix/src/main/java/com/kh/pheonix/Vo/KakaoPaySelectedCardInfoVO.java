package com.kh.pheonix.Vo;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class KakaoPaySelectedCardInfoVO {
	private String cardBin;//카드 BIN
	private int installMonth;//할부 개월 수
	private String installmentType; //할부 유형 CARD_INSTALLMENT: 업종 무이자, SHARE_INSTALLMENT: 분담 무이자
	private String cardCropName;//카드사 정보
	private String interestFreeInstall;//무이자할부 여부(Y/N)
}

