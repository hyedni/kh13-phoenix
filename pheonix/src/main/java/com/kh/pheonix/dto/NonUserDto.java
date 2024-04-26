package com.kh.pheonix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class NonUserDto {
	private String NonUserEmail;
	private int NonUserBirth;
	private int NonUserPw;
}
