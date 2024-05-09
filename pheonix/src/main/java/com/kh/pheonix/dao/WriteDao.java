package com.kh.pheonix.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.WriteDto;

@Repository
public class WriteDao {

	@Autowired
	private SqlSession sqlSession;
	
	public int sequence () {
		return sqlSession.selectOne("write.sequence");
	}
	
	//조회
	public List<WriteDto> selectList() {
		return sqlSession.selectList("write.list");
	}
	
	//등록
	public void insert(WriteDto writeDto) {
		sqlSession.insert("write.save", writeDto);
	}
	
	//수정
	public boolean update(WriteDto writeDto) {
		return sqlSession.update("write.edit", writeDto) > 0;
	}
	
	//삭제
	public boolean delete(int writeNo) {
		return sqlSession.delete("write.delete", writeNo) >0;
	}

	public WriteDto selectOne(int writeNo) {
		return sqlSession.selectOne("write.find", writeNo);
	}
	
	public void insertReply(WriteDto writeDto) {
	    sqlSession.insert("write.reply", writeDto);
	}


}
