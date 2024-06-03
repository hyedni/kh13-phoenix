package com.kh.pheonix.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class MovieListVo {

	private String movieTitle;
	private int movieNo;
	private String movieAge;
	private String movieScreenType;
}
