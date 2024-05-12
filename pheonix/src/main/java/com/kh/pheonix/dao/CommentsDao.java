package com.kh.pheonix.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.CommentsDto;

@Repository
public class CommentsDao {

	@Autowired
	private SqlSession sqlSession;
	
	public int sequence () {
		return sqlSession.selectOne("comments.sequence");
	}
	
	//조회
	public List<CommentsDto> selectList() {
		return sqlSession.selectList("comments.list");
	}
	
	//등록
	public void insert(CommentsDto commentsDto) {
		sqlSession.insert("comments.save", commentsDto);
	}
	
	//수정
	public boolean update(CommentsDto commentsDto) {
		return sqlSession.update("comments.edit", commentsDto) > 0;
	}
	//삭제
	public boolean delete(int commentsId) {
		return sqlSession.delete("comments.delete", commentsId) > 0;
	}
	}

