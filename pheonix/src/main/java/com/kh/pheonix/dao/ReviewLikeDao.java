package com.kh.pheonix.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewLikeDao {

	@Autowired
	private SqlSession sqlSession;
	
	//좋아요 내역 등록
	public void insert(String userId, int reviewNo) {
		Map<String, Object> data = new HashMap<>();
		data.put("userId", userId);
		data.put("reviewNo", reviewNo);
		sqlSession.insert("review.likeInsert", data);
	}
	
	//좋아요 내역 삭제
	public int delete(String userId, int reviewNo) {
		Map<String, Object> data = new HashMap<>();
		data.put("userId", userId);
		data.put("reviewNo", reviewNo);
		return sqlSession.delete("review.likeDelete", data);
	}
	
	//리뷰 좋아요 여부 확인
	public boolean check(String userId, int reviewNo) {
		Map<String, Object> data = new HashMap<>();
		data.put("userId", userId);
		data.put("reviewNo", reviewNo);
		return sqlSession.selectOne("review.likeCheck", data);
	}
	
	//특정 리뷰의 좋아요 개수 확인
	public int count(int reviewNo) {
		return sqlSession.selectOne("review.likeCount", reviewNo);
	}
}
