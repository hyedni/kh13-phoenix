package com.kh.pheonix.service;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.pheonix.configuration.JwtProperties;
import com.kh.pheonix.dto.SocialUserDto;
import com.kh.pheonix.dto.UserDto;
import com.kh.pheonix.vo.UserLoginVO;

import io.jsonwebtoken.Claims;
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
	
	public String createRefreshToken(UserDto userDto) {
		//1. 서명을 위한 키(SecretKey) 생성
		// - HMAC : 메세지의 무결성과 인증을 동시에 처리하기 위해 키를 사용하는 암호화 방식
		// - HMAC-SHA 라는 이름으로 시작함
		String keyStr = jwtProperties.getKeyStr();
		SecretKey key = 
				Keys.hmacShaKeyFor(keyStr.getBytes(StandardCharsets.UTF_8));
		//2. 토큰의 만료시간 설정 (java.util.Date)
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();//현재시각 추출
		c.add(Calendar.HOUR, jwtProperties.getExpireHourRefresh());
		Date expire = c.getTime();//만료시각 추출
		//3. 토큰 생성
		String token = Jwts.builder()
					.issuer(jwtProperties.getIssuer())//발행자
					.issuedAt(now)//발행시각
					.expiration(expire)//만료시각
					.signWith(key)//서명
					.claim("userId", userDto.getUserId())//사용자에게 보낼 내용(key=value)
					.claim("userGrade", userDto.getUserGrade())//사용자에게 보낼 내용(key=value)
				.compact();
		
		return token;
	}
	
	public UserLoginVO parse(String token) {
		//key 생성
		String keyStr = jwtProperties.getKeyStr();
		SecretKey key = Keys.hmacShaKeyFor(keyStr.getBytes(StandardCharsets.UTF_8));
		//토큰 해석
		Claims claims = (Claims) Jwts.parser()//도구준비
									.verifyWith(key)//열쇠 설정
									.requireIssuer(jwtProperties.getIssuer())//발행자 정보 확인
									.build()//만듦
									.parse(token)//토큰 해석
									.getPayload();//내용 가져오기
									
		//해석된 결과 객체로 반환
		return UserLoginVO.builder()
								.userId((String)claims.get("userId"))
								.userGrade((String)claims.get("userGrade"))
							.build();
	}
	
	
	//소셜로그인 토큰생성
	public String createSocialAccessToken(SocialUserDto socialUserDto) {
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
				.claim("userId", socialUserDto.getSocialEmail())
				.claim("userGrade", socialUserDto.getSocialName())
			.compact();

		return token;	
	}
	
	public String createSocialRefreshToken(SocialUserDto socialUserDto) {
		//1. 서명을 위한 키(SecretKey) 생성
		// - HMAC : 메세지의 무결성과 인증을 동시에 처리하기 위해 키를 사용하는 암호화 방식
		// - HMAC-SHA 라는 이름으로 시작함
		String keyStr = jwtProperties.getKeyStr();
		SecretKey key = 
				Keys.hmacShaKeyFor(keyStr.getBytes(StandardCharsets.UTF_8));
		//2. 토큰의 만료시간 설정 (java.util.Date)
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();//현재시각 추출
		c.add(Calendar.HOUR, jwtProperties.getExpireHourRefresh());
		Date expire = c.getTime();//만료시각 추출
		//3. 토큰 생성
		String token = Jwts.builder()
					.issuer(jwtProperties.getIssuer())//발행자
					.issuedAt(now)//발행시각
					.expiration(expire)//만료시각
					.signWith(key)//서명
					.claim("userId", socialUserDto.getSocialEmail())//사용자에게 보낼 내용(key=value)
					.claim("userGrade",socialUserDto.getSocialName())//사용자에게 보낼 내용(key=value)
				.compact();
		
		return token;
	}

		
}
