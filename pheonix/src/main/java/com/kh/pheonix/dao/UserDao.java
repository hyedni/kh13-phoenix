package com.kh.pheonix.dao;

import java.util.HashMap;
import java.util.List;
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
	
	//검색
	public UserDto selectOne(String userId) {
		return sqlSession.selectOne("user.find", userId);
	}
	
	//조회
	public List<UserDto> selectList(){
		return sqlSession.selectList("user.list");
	}
	
	//프로필이미지 연결
	public void connect(String userId, int attachNo) {
		Map<String, Object> parameters = new HashMap<>();
	    parameters.put("userId", userId);
	    parameters.put("attachNo", attachNo);
	    sqlSession.insert("user.connect", parameters);
	}
	
	//attach 테이블의 번호 찾기
	public int findAttachNo (String userId) {
		return sqlSession.selectOne("user.findAttachNo", userId);
	}
	
}
  