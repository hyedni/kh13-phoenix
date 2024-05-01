package com.kh.pheonix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class UserCertDto {
	private String certEmail;
	private String certCode;
	private String certTime;
	private boolean cert;
	private String certCreate;
	private String userType;
}
