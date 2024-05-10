package com.kh.pheonix.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class KakaoPayConfiguration {
	
	@Autowired
	private KakaoPayProperties kakaoPayProperties;
	
	@Bean
	public RestTemplate template() {
		return new RestTemplate();
	}
	
	@Bean
	public HttpHeaders header() {
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "SECRET_KEY "+kakaoPayProperties.getKey());
		header.add("Content-Type", "application/json");
		return header;
	}
}
