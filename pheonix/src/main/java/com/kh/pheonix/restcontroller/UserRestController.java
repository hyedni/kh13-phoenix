package com.kh.pheonix.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.Vo.UserLoginVO;
import com.kh.pheonix.dao.UserDao;
import com.kh.pheonix.dto.UserDto;
import com.kh.pheonix.service.JwtService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserRestController {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/login")
	public ResponseEntity<UserLoginVO>login(@RequestBody UserDto userDto){
		UserDto findDto = userDao.selectOne(userDto.getUserId());
		if(findDto == null) {
			return ResponseEntity.notFound().build();
		}
		
		boolean isValid = findDto.getUserPw().equals(userDto.getUserPw());
		
		if(isValid) {
			String accessToken = jwtService.createAccessToken(findDto);
			String refreashToken = jwtService.createRefreshToken(findDto);
			
			return ResponseEntity.ok().body(UserLoginVO.builder()
						.userId(findDto.getUserId())
						.userGrade(findDto.getUserGrade())
						.accessToken(accessToken)
						.refreshToken(refreashToken)
						.build());//200
		}
		else {
				return ResponseEntity.status(401).build();
			}		
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<UserLoginVO>refreash(@RequestHeader("Autorization") String refreshToken){
		try {
			UserLoginVO loginVO = jwtService.parse(refreshToken);
			UserDto userDto = userDao.selectOne(loginVO.getUserId());
			if(userDto == null) {
				throw new Exception("존재하지 않는 아이디");
			}
			if(!loginVO.getUserGrade().equals(userDto.getUserGrade())) {
				throw new Exception("정보 불일치");
			}
			
			String accessToken = jwtService.createAccessToken(userDto);
			String newRefreshToken = jwtService.createRefreshToken(userDto);
			return ResponseEntity.ok().body(UserLoginVO.builder()
						.userId(userDto.getUserId())
						.userGrade(userDto.getUserGrade())
						.accessToken(accessToken)
						.refreshToken(newRefreshToken)
						.build());	
		}
		
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(401).build();
		}
		
	}
}
	



