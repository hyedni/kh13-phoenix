package com.kh.pheonix.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kh.pheonix.dao.MovieDao;
import com.kh.pheonix.dao.NonUserAuthorizationDao;
import com.kh.pheonix.dao.NonUserCertDao;
import com.kh.pheonix.dao.UserCertDao;

@Service
public class ScheduleService {

	@Autowired
	private UserCertDao userCertDao;
	@Autowired
	private NonUserCertDao nonUserCertDao;
	@Autowired
	private NonUserAuthorizationDao nonUserAuthorizationDao;
	
	@Autowired
	private MovieDao movieDao;
	
	@Scheduled(cron ="0 0 * * * *")//100초마다
	public void clearExpiredCerts() {
		System.out.println("5분 지난 인증번호 삭제합니다");
		// 모든 만료된 인증번호를 삭제
		userCertDao.deleteExpiredCert();
		nonUserCertDao.deleteExpiredCert();
	}
	
	@Scheduled(cron = "0 0 * * * *")
	public void clearToken() {
		System.out.println("비회원 토큰 삭제합니다");
		nonUserAuthorizationDao.deleteExpiredAuth();
	}
	
	@Scheduled(cron ="0 0 0 * * ?") //자정
	public void updateMovieOn () {
		LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ISO_LOCAL_DATE); // yyyy-MM-dd 형태로 포맷
		movieDao.updateMovieOn(formattedDate);
		System.out.println(formattedDate + "상영중 업뎃완료");
	}
	
}
