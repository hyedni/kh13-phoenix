package com.kh.pheonix.kakaoPayVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PurchaseVO {//전송되는 이름과 같은 변수명
	private int no;
	private int qty;
}
