package com.kh.pheonix.service;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.pheonix.configuration.JwtProperties;
import com.kh.pheonix.dto.UserDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Autowired
	private JwtProperties jwtProperties;
	
	public String createAccessToken(UserDto userDto) {
		//키생성
		String keyStr = jwtProperties.getKeyStr();
		SecretKey key = 
				Keys.hmacShaKeyFor(keyStr.getBytes(StandardCharsets.UTF_8));
		//토큰 만료시간 설정
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();//현재
		c.add(Calendar.HOUR, jwtProperties.getExpireHour());
		Date expire = c.getTime();	//만료
		
		//토큰 생성
		String token = Jwts.builder()
				.issuer(jwtProperties.getIssuer())
				.issuedAt(now)
				.expiration(expire)
				.signWith(key)
				.claim("userId", userDto.getUserId())
				.claim("userGrade", userDto.getUserGrade())
			.compact();

		return token;	
	}
}
