package com.kh.pheonix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class NonUserCertDto {
    private String nonUserCertEmail;
    private String nonUserCertCode;
    private String nonUserCertTime;
    private String nonUserCreate;
}