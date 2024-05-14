package com.kh.pheonix.service;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.stereotype.Service;

@Service
public class NonUserTokenService {

    private static final int TOKEN_LENGTH = 16; // 토큰 길이

    // 랜덤 토큰 생성 메서드
    public String generateRandomToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[TOKEN_LENGTH];
        secureRandom.nextBytes(tokenBytes);
        return new BigInteger(1, tokenBytes).toString(16);
    }

    // 테스트를 위한 메인 메서드
    public static void main(String[] args) {
        NonUserTokenService tokenService = new NonUserTokenService();
        String randomToken = tokenService.generateRandomToken();
        System.out.println("Random Token: " + randomToken);
    }
}
