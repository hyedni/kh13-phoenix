package com.kh.pheonix.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.Vo.UserReviewVO;
import com.kh.pheonix.dto.ReviewDto;

@Repository
public class ReviewDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int sequence() {
		return sqlSession.selectOne("review.sequence");
	}
	public void insert(ReviewDto reviewDto) {
		sqlSession.insert("review.insert", reviewDto);
	}
	
	//리뷰 작성 여부 판별
	public boolean findReview (int movieNo, String reviewWriter) {
		Map <String, Object> data = new HashMap<>();
		data.put("reviewWriter", reviewWriter);
		data.put("movieNo", movieNo);
		ReviewDto result = sqlSession.selectOne("review.findReview", data);
		if(result == null) {
			return true;
		}
		return false;
	}
	
	public List<UserReviewVO> listByMovie(int movieNo) {
		return sqlSession.selectList("review.reviewListByMovie", movieNo);
	}
	
	public void delete(int reviewNo) {
		sqlSession.delete("review.delete", reviewNo);
	}
	
	//포인트주기
	public void updatePoint (String userId) {
		sqlSession.update("review.updatePoint", userId);
	}
}
