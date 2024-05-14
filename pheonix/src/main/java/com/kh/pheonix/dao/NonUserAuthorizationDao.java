package com.kh.pheonix.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.NonUserAuthorizationDto;

@Repository
public class NonUserAuthorizationDao {
	@Autowired
	private SqlSession sqlSession;
	
	//토큰값등록
	public void insert(NonUserAuthorizationDto nonUserAuthorizationDto) {
		sqlSession.insert("nonUserAuthorization.insert", nonUserAuthorizationDto);
	}
	
	//토큰있는지검색 
	public NonUserAuthorizationDto selectOne(String nonUserId) {
		return sqlSession.selectOne("nonUserAuthorization.selectOne", nonUserId);
	}

	//10분 지난거 삭제
	public boolean deleteExpiredAuth() {
		return sqlSession.delete("nonUserAuthorization.deleteExpiredAuth")>0;
	}
	
}
