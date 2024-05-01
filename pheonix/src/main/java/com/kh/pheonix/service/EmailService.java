package com.kh.pheonix.service;

import java.text.DecimalFormat;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.kh.pheonix.dao.UserCertDao;
import com.kh.pheonix.dto.UserCertDto;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender sender;
	
	//임시 비밀번호 발송
	public void sendTempPassword(String email) {
		String lower = "abcdefghijklmnopqrstuvwxyz";//3글자
		String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";//3글자
		String number = "0123456789";//1글자
		String special = "!@#$";//1글자
		
		Random r = new Random();//랜덤 도구
		StringBuffer buffer = new StringBuffer();//문자열 합성도구
		
		for(int i=0; i < 3; i++) {
			int pos = r.nextInt(lower.length());//lower에서의 랜덤위치
			buffer.append(lower.charAt(pos));//버퍼에 추가
		}
		for(int i=0; i < 3; i++) {
			int pos = r.nextInt(upper.length());//upper에서의 랜덤위치
			buffer.append(upper.charAt(pos));//버퍼에 추가
		}
		for(int i=0; i < 1; i++) {
			int pos = r.nextInt(number.length());//number에서의 랜덤위치
			buffer.append(number.charAt(pos));//버퍼에 추가
		}
		for(int i=0; i < 1; i++) {
			int pos = r.nextInt(special.length());//special에서의 랜덤위치
			buffer.append(special.charAt(pos));//버퍼에 추가
		}
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("[KH정보교육원] 임시 비밀번호 안내");
		message.setText("임시 비밀번호는 " + buffer + "입니다");
		
		sender.send(message);
	}
	
	//인증번호 발송
	@Autowired
	private UserCertDao userCertDao;
	
	//인증번호 발송 - 주어진 이메일에 무작위 6자리 숫자를 전송
	public void sendCert(String certEmail) {
		Random r = new Random();
		int number = r.nextInt(1000000); //000000~999999
		DecimalFormat fmt = new DecimalFormat("000000");
		
		//메일 발송
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(certEmail);
		message.setSubject("[Pheonix] 인증번호 안내");
		message.setText("인증번호는 [" + fmt.format(number) + "] 입니다");
		
		sender.send(message);
		
		//인증번호 저장 - 기존 내역 삭제 후 저장
		userCertDao.delete(certEmail); // 기존의 모든 만료된 인증번호를 삭제합니다.
		UserCertDto userCertDto = new UserCertDto();
		userCertDto.setCertEmail(certEmail);
		userCertDto.setCertCode(fmt.format(number)); // 새로운 인증번호를 설정합니다.
		userCertDao.insert(userCertDto); // 새로운 인증번호를 저장합니다.
		
	}
	
}