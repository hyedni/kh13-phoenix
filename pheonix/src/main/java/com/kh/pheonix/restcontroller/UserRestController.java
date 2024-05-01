package com.kh.pheonix.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.Vo.UserLoginVO;
import com.kh.pheonix.dao.UserCertDao;
import com.kh.pheonix.dao.UserDao;
import com.kh.pheonix.dto.UserCertDto;
import com.kh.pheonix.dto.UserDto;
import com.kh.pheonix.service.EmailService;
import com.kh.pheonix.service.JwtService;
import com.kh.pheonix.service.UserService;


@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserRestController {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserService userService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private UserCertDao userCertDao;
	
	//회원가입
	@PostMapping("/join")
	public ResponseEntity<String> join(@RequestBody UserDto userDto) {
	    // 사용자 등록을 위한 서비스 메서드 호출
		System.out.println(userDto);
	    boolean isRegistered = userService.registerUser(userDto);
	    if (isRegistered) {
	        return ResponseEntity.ok().body("회원가입이 완료되었습니다.");
	    } else {
	        return ResponseEntity.badRequest().body("회원가입에 실패하였습니다.");
	    }
	}
	
	//메일보내기
	@PostMapping("/sendCert")
    public void sendCert(@RequestParam String certEmail) {
        UserDto userDto = userDao.selectOne(certEmail);//memberEmail에 있는지 찾기 없으면 null이 뜰것임
        if (userDto == null) {
            // 중복된 이메일이 없는 경우에만 이메일을 보내기
            emailService.sendCert(certEmail); 
        }  
    }
	
	@PostMapping("/checkCert")
	public ResponseEntity<String> checkCert(@RequestBody UserCertDto userCertDto) {
	    boolean isValid = userCertDao.checkValid(userCertDto);
	    if (isValid) { // 인증 성공 시 인증번호 삭제
	        userCertDao.delete(userCertDto.getCertEmail());
	        return ResponseEntity.ok("인증 성공");
	    } else {
	        return ResponseEntity.badRequest().body("유효하지 않은 인증번호입니다.");
	    }
	}
	
	
	
	 //로그인
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
	



