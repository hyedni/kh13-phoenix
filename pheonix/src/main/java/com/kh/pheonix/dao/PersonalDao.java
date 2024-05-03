package com.kh.pheonix.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.PersonalDto;

@Repository
public class PersonalDao {

	@Autowired 
	private SqlSession sqlSession;
	
	public int sequence () {
		return sqlSession.selectOne("personal.sequence");
	}
	
	//조회
	public List<PersonalDto> selectList() {
		return sqlSession.selectList("personal.list");
	}
	
	//등록
	public void insert(PersonalDto personalDto) {
		sqlSession.insert("personal.save", personalDto);
	}
	
	//수정
	public boolean update(PersonalDto personalDto) {
		return sqlSession.update("personal.edit", personalDto) > 0;
	}
	
	//삭제
	public boolean delete(int personalNo) {
		return sqlSession.delete("personal.delete", personalNo) > 0;
	}
	
	public PersonalDto selectOne(int personalNo) {
		return sqlSession.selectOne("personal.find", personalNo);
	}
	
	public List<PersonalDto> replyList() {
		return sqlSession.selectList("personal.reply");
	}
	}

