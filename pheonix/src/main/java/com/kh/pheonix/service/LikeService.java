package com.kh.pheonix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.pheonix.Vo.UserReviewVO;
import com.kh.pheonix.dao.ReviewLikeDao;

@Service
public class LikeService {

	@Autowired
	private ReviewLikeDao reviewLikeDao;
	
	public List<UserReviewVO> check (List<UserReviewVO> userReviewVO) {
		List<UserReviewVO> list = userReviewVO;
		
		for(UserReviewVO vo : list) {
			vo.setState(reviewLikeDao.check(vo.getUserId(), vo.getReviewNo()));
			vo.setCount(reviewLikeDao.count(vo.getReviewNo()));
		}
		return list;
	}
}
