package com.kh.pheonix.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class NonUserDto {
    private String nonUserEmail;
    private String nonUserBirth;
    private String nonUserPw;
    private String nonUserLogin;
    private String nonUserOut;
    
    private String token;
}