package com.kh.pheonix.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.Vo.UserLoginVO;
import com.kh.pheonix.dao.SocialUserDao;
import com.kh.pheonix.dao.UserCertDao;
import com.kh.pheonix.dao.UserDao;
import com.kh.pheonix.dto.SocialUserDto;
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
	@Autowired
	private SocialUserDao socialUserDao;


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
		System.out.println(userCertDto);
	    boolean isValid = userCertDao.checkValid(userCertDto.getCertEmail(), userCertDto.getCertCode());
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
			return ResponseEntity.status(404).build();
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
	public ResponseEntity<UserLoginVO>refreash(@RequestHeader("Authorization") String refreshToken){
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
	
	
	@GetMapping("/socialLogin")
    public ResponseEntity<UserLoginVO> socialLogin(@RequestBody SocialUserDto socialUserDto){
		System.out.print(socialUserDto);
		
		//회원한테 받은 socialEmail ==null이면 false
		if(socialUserDto.getSocialEmail() == null) {
			return ResponseEntity.status(401).build();
		}
		else {// !=null이면 판정 1. userEmail에 저장된건지
			UserDto userDto = new UserDto(); // UserDto의 인스턴스를 생성
			String userEmail = userDto.getUserEmail();
			
			boolean isValid = socialUserDto.getSocialEmail().equals(userEmail);
			//2. userEmail에 저장됐으면 == 이미 가입된 회원
			
			if(!isValid) {//3. 저장된게 없으면 social에 저장된건지
				//찾기
				SocialUserDto emailFind = socialUserDao.selectOne(socialUserDto.getSocialEmail());
				
				//찾았는데 없어
				if(emailFind != null) {
					
				socialUserDao.insert(socialUserDto);	
				}
				
				//jwt토큰 발급
				SocialUserDto findDto = socialUserDao.selectOne(socialUserDto.getSocialEmail());
				
				String accessToken = jwtService.createSocialAccessToken(findDto);
				String refreashToken = jwtService.createSocialRefreshToken(findDto);
				
				return ResponseEntity.ok().body(UserLoginVO.builder()
							.userId(findDto.getSocialEmail())
							.userGrade(findDto.getSocialName())
							.accessToken(accessToken)
							.refreshToken(refreashToken)
						.build());//200

			}
				
		}
		return null;
	
    }
	
	
	
	
}
	



