package com.kh.pheonix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.pheonix.dao.ReviewLikeDao;
import com.kh.pheonix.vo.UserReviewVO;

@Service
public class LikeService {

	@Autowired
	private ReviewLikeDao reviewLikeDao;
	
	public List<UserReviewVO> check (List<UserReviewVO> userReviewVO, String userId) {
//		List<UserReviewVO> list = userReviewVO;
		
		for(UserReviewVO vo : userReviewVO) {
			vo.setState(reviewLikeDao.check(userId, vo.getReviewNo()));
			vo.setCount(reviewLikeDao.count(vo.getReviewNo()));
		}
		return userReviewVO;
	}
}
