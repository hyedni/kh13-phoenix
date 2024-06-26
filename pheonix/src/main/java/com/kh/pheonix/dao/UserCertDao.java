package com.kh.pheonix.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.UserCertDto;
import com.kh.pheonix.dto.UserDto;

@Repository
public class UserCertDao {

	@Autowired
	private SqlSession sqlSession;
	
	//등록
	public void insert(UserCertDto userCertDto) {
		sqlSession.insert("userCert.insert", userCertDto);
	}
	
	//삭제
	public boolean delete(String certEmail) {
		return sqlSession.delete("userCert.delete", certEmail) > 0;
	}
	
	// 검색
	public UserDto selectOne(String certEmail) {
		return sqlSession.selectOne("userCert.selectOne", certEmail);
	}

	//전송된 인증번호 확인
		public boolean checkValid(String certEmail, String certCode) {
			Map<String, String> params = new HashMap<>();
		    params.put("certEmail", certEmail);
		    params.put("certCode", certCode);
		    
		UserCertDto result = sqlSession.selectOne("userCert.checkValid", params);
		   boolean isValid =  result != null;
		   return isValid;
		}
	
	//5분 지난거 삭제
	public boolean deleteExpiredCert() {
	    return sqlSession.delete("userCert.deleteExpiredCert") > 0;
	}

	
}
