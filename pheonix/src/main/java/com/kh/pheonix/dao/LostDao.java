package com.kh.pheonix.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.LostDto;

@Repository
public class LostDao {

	@Autowired
	private SqlSession sqlSession;
	
	public int sequence() {
		return sqlSession.selectOne("lost.sequence");
	}
	
	//조회
	public List<LostDto> selectList() {
		return sqlSession.selectList("lost.list");
	}
	
	//등록
	public void insert(LostDto lostDto) {
		sqlSession.insert("lost.save", lostDto);
	}
	
	//수정
	public boolean update(LostDto lostDto) {
		return sqlSession.update("lost.edit", lostDto) > 0;
		
	}
	
	//삭제
	public boolean delete(int lostNo) {
		return sqlSession.delete("lost.delete", lostNo) > 0;
		
	}
	
	public LostDto selectOne(int lostNo) {
		return sqlSession.selectOne("lost.find", lostNo);
	}
	
	public LostDto find (int lostNo) {
		return sqlSession.selectOne("lost.find", lostNo);
	}
	
	public void connect (int lostNo, int attachNo) {
		Map<String, Object> data = new HashMap<>();
		data.put("lostNo", lostNo);
		data.put("attachNo", attachNo);
		sqlSession.insert("lost.connect", data);
	}


	}

