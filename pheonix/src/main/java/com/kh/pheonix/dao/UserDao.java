package com.kh.pheonix.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.UserDto;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//등록
	public void insert(UserDto userDto) {
		sqlSession.insert("user.input", userDto);
	}
	
	//삭제
	public boolean delete(String userId) {
		return sqlSession.delete("user.delete", userId) > 0;
	}
	
	//수정
	public boolean edit(UserDto userDto) {
		return sqlSession.update("user.edit", userDto) > 0;
	}
	
	//검색
	
	
}
  