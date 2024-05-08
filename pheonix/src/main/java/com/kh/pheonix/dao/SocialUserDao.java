package com.kh.pheonix.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.SocialUserDto;

@Repository
public class SocialUserDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//가입
	public void insert(SocialUserDto socialUserDto) {
		sqlSession.insert("socialUser.insert", socialUserDto);
	}
	
	//판정
	public SocialUserDto selectOne(String socialEmail) {
		return sqlSession.selectOne("socialUser.find", socialEmail);
	}
	
	//삭제
	public boolean delete(String socialEmail) {
		return sqlSession.delete("socialUser.delete", socialEmail) > 0;
	}
	
	//수정
	public boolean edit(String socialName) {
		return sqlSession.update("socialUser.update", socialName) > 0;
	}
}
