package com.kh.pheonix.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@JsonIgnoreProperties
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class UserCertDto {
	private String certEmail;
	private String certCode;
	private String certTime;
	private String certCreate;
}
