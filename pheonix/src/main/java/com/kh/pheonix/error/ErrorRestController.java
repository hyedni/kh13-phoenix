package com.kh.pheonix.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kh.pheonix.service.BookingService;

@RestControllerAdvice(basePackageClasses = {BookingService.class})
public class ErrorRestController {

	@ExceptionHandler(Exception.class) 
	public ResponseEntity<String> handler (Exception e) {
		
		return ResponseEntity.status(500).body("정상적인 접근이 아닙니다.");
	}
}
