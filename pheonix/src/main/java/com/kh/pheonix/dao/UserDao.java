package com.kh.pheonix.dao;

import java.util.HashMap;
import java.util.Map;

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
	
	//포인트 충전
	public boolean editPoint(int userPoint, String userId) {
		Map<String, Object> info = new HashMap<>();
		info.put("userPoint", userPoint);
		info.put("userId", userId);
		return sqlSession.update("user.editPoint", info) > 0;
	}
	//포인트 사용
	public boolean usedPoint(String userId, int userPoint) {
		Map<String, Object> info = new HashMap<>();
		info.put("userPoint", userPoint);
		info.put("userId", userId);
		return sqlSession.update("user.usedPoint", info) > 0;
	}
	//검색
	public UserDto selectOne(String userId) {
		return sqlSession.selectOne("user.find", userId);
	}
	
}
  