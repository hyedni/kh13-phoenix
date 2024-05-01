package com.kh.pheonix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kh.pheonix.dao.UserCertDao;

@Service
public class ScheduleService {

	@Autowired
	private UserCertDao userCertDao;
	
	@Scheduled(cron ="0 0 * * * *")//100초마다
	public void clearExpiredCerts() {
		System.out.println("5분 지난 인증번호 삭제합니다");
		// 모든 만료된 인증번호를 삭제
		userCertDao.deleteExpiredCert();
	}
	
}
