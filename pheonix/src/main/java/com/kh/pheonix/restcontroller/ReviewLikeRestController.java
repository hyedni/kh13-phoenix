package com.kh.pheonix.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.dao.ReviewLikeDao;
import com.kh.pheonix.vo.LikeInfoVO;
import com.kh.pheonix.vo.LikeVO;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/review_like")
public class ReviewLikeRestController {
	
	@Autowired
	private ReviewLikeDao reviewLikeDao;
		
	
	@GetMapping("/")
	public LikeVO toggle(@RequestBody LikeInfoVO info) {
		LikeVO likeVO = new LikeVO();
		
		if(reviewLikeDao.check(info.getUserId(), info.getReviewNo())) { //좋아요 누른 적 있음
			reviewLikeDao.delete(info.getUserId(), info.getReviewNo()); //좋아요 취소
			likeVO.setState(false);
		}
		else {
			reviewLikeDao.insert(info.getUserId(), info.getReviewNo());
			likeVO.setState(true);
		}
		likeVO.setCount(reviewLikeDao.count(info.getReviewNo()));
		likeVO.setReviewNo(info.getReviewNo());
		return likeVO;
	}
	
	
}
