package com.kh.pheonix.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.pheonix.service.JwtService;
import com.kh.pheonix.vo.UserLoginVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserInterceptor  implements HandlerInterceptor{
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String method = request.getMethod();
		if(method.toLowerCase().equals("options")) {
			return true;
		}
		
		String token = request.getHeader("Authorization");
		if(token == null) {//헤더가 없으면 비회원이라는 뜻
			response.sendError(401);
			return false;
		}
		
		try {
			//토큰 해석 시도
			UserLoginVO loginVO = jwtService.parse(token);
			log.debug("아이디 = {}, 등급 = {}", loginVO.getUserId(), loginVO.getUserGrade());
			//추가적으로 DB검사, 기타 처리를 추가할 수 있다
			return true;//통과
		}
		catch(Exception e) {
			//토큰이 유효하지 않은 경우 - 시간만료, 변조, 항목 누락, ...
			response.sendError(403);
			return false;
		}
	}
}
