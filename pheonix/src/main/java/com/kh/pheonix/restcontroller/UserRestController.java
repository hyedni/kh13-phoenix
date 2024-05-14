package com.kh.pheonix.restcontroller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.pheonix.Vo.UserLoginVO;
import com.kh.pheonix.dao.NonUserCertDao;
import com.kh.pheonix.dao.NonUserDao;
import com.kh.pheonix.dao.SocialUserDao;
import com.kh.pheonix.dao.UserCertDao;
import com.kh.pheonix.dao.UserDao;
import com.kh.pheonix.dto.NonUserCertDto;
import com.kh.pheonix.dto.NonUserDto;
import com.kh.pheonix.dto.UserCertDto;
import com.kh.pheonix.dto.UserDto;
import com.kh.pheonix.service.AttachService;
import com.kh.pheonix.service.EmailService;
import com.kh.pheonix.service.ImageService;
import com.kh.pheonix.service.JwtService;
import com.kh.pheonix.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

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
	@Autowired
	private AttachService attachService;
	@Autowired
	private ImageService imageService;

	// 회원가입
	@PostMapping("/join")
	public ResponseEntity<String> join(@ModelAttribute UserDto userDto, 
		@RequestParam(value = "attach", required = false) MultipartFile attach)
		throws IllegalStateException, IOException {

		boolean isRegistered = userService.registerUser(userDto);//저장됨
		System.out.println(userDto);
		
		// 회원으로부터 첨부파일을 받으면 attach 파일에 저장해
		if (attach != null && !attach.isEmpty()) {
			int attachNo = attachService.save(attach); // 파일저장 + DB저장
			System.out.print("의심병1 =" + attachNo);
			System.out.print("의심병2 =" + userDto.getUserId());

			userDao.connect(userDto.getUserId(), attachNo); // 연결

		}
		
		if (isRegistered) {	
			return ResponseEntity.ok().body("회원가입이 완료되었습니다.");
		} else {
			return ResponseEntity.badRequest().body("회원가입에 실패하였습니다.");
		}
	
	}

	// 메일보내기
	@PostMapping("/sendCert")
	public void sendCert(@RequestParam String certEmail) {
		UserDto userDto = userDao.selectOne(certEmail);// memberEmail에 있는지 찾기 없으면 null이 뜰것임
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

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<UserLoginVO> login(@RequestBody UserDto userDto, HttpServletRequest request) {
		UserDto findDto = userDao.selectOne(userDto.getUserId());

		if (findDto == null) {
			return ResponseEntity.status(404).build();
		}

		boolean isValid = findDto.getUserPw().equals(userDto.getUserPw());

		if (isValid) {
			String accessToken = jwtService.createAccessToken(findDto);
			String refreashToken = jwtService.createRefreshToken(findDto);

			return ResponseEntity.ok().body(UserLoginVO.builder().userId(findDto.getUserId())
					.userGrade(findDto.getUserGrade()).accessToken(accessToken).refreshToken(refreashToken).build());// 200
		} else {
			return ResponseEntity.status(401).build();
		}
	}

	@PostMapping("/refresh")
	public ResponseEntity<UserLoginVO> refreash(@RequestHeader("Authorization") String refreshToken) {
		try {
			UserLoginVO loginVO = jwtService.parse(refreshToken);
			UserDto userDto = userDao.selectOne(loginVO.getUserId());
			if (userDto == null) {
				throw new Exception("존재하지 않는 아이디");
			}
			if (!loginVO.getUserGrade().equals(userDto.getUserGrade())) {
				throw new Exception("정보 불일치");
			}

			String accessToken = jwtService.createAccessToken(userDto);
			String newRefreshToken = jwtService.createRefreshToken(userDto);
			return ResponseEntity.ok().body(UserLoginVO.builder().userId(userDto.getUserId())
					.userGrade(userDto.getUserGrade()).accessToken(accessToken).refreshToken(newRefreshToken).build());
		}

		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(401).build();
		}

	}

	// 조회
	@GetMapping("/mypage")
	public UserDto find(@RequestParam String userId) {
		UserDto userDto = userDao.selectOne(userId);
		UserDto urlDto = imageService.UserImgbyOne(userDto);
		return userDto;
	}
	
	
	//변경
	@PatchMapping("/mypage")
	public boolean edit(UserDto userDto) {
		boolean result = userDao.edit(userDto);
		return result;
	}
	
//	@Autowired
//	private SocialLoginDao socialLoginDao;
	
//	//토큰 저장
//	@GetMapping("/saveToken")
//	public ResponseEntity<?> saveToken(@RequestBody GoogleInfResponse googleResponse) {
//		//클라이언트부터 값이 들어오면 저장
//		socialLoginDao.insert(googleResponse);
//	ㅁㅁㅁ
//	}
	
	@Autowired
	private NonUserDao nonUserDao;
	@Autowired
	private NonUserCertDao nonUserCertDao;
	
	@PostMapping("/nonUserJoin")
	public ResponseEntity<String> nonUserJoin(@RequestBody NonUserDto nonUserDto) {
	    // 비회원 정보 저장
	    nonUserDao.insert(nonUserDto);
	    nonUserDao.selectOne(nonUserDto.getNonUserEmail());
	     
	    return ResponseEntity.ok("비회원 정보가 저장되었습니다.");
	    
	}
	
	@PostMapping("/nonUserSend")
	public void nonUserSend(@RequestParam String nonUserCertEmail) {
		NonUserDto nonUserDto = nonUserDao.selectOne(nonUserCertEmail);
		if(nonUserDto == null) {
			//중복된거 없으면
			emailService.nonUserSendCert(nonUserCertEmail);
		}
	}
	@PostMapping("/nonUserCheck")
	public ResponseEntity<String> nonUserCheck(@RequestBody NonUserCertDto nonUserCertDto){
		System.out.println(nonUserCertDto);
		boolean isValid = nonUserCertDao.checkValid(nonUserCertDto.getNonUserCertEmail(), nonUserCertDto.getNonUserCertCode());
		if(isValid) {
			nonUserCertDao.delete(nonUserCertDto.getNonUserCertEmail());
			return ResponseEntity.ok("인증 성공");
		} else {
			return ResponseEntity.badRequest().body("유효하지 않은 인증번호입니다.");
		}
	}
	
	
	
	
	
	
	
	
	
	

}