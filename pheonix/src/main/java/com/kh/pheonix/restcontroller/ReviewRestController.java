package com.kh.pheonix.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.Vo.UserLoginVO;
import com.kh.pheonix.Vo.UserReviewVO;
import com.kh.pheonix.dao.ReviewDao;
import com.kh.pheonix.dao.ReviewLikeDao;
import com.kh.pheonix.dto.ReviewDto;
import com.kh.pheonix.service.ImageService;
import com.kh.pheonix.service.JwtService;
import com.kh.pheonix.service.LikeService;

@CrossOrigin
@RestController
@RequestMapping("/review")
public class ReviewRestController {
	
	@Autowired
	private ReviewDao reviewDao;
	
	@Autowired
	private ReviewLikeDao reviewLikeDao;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private LikeService likeService;
	
	@Autowired
	private JwtService jwtService;
	
	//이미 등록된 후기인지 검사하는걸 따로 빼서 검사하고, 프론트에서 alert띄움 
	@PostMapping("/valid")
	public boolean valid (@RequestBody ReviewDto reviewDto) {
		boolean isValid =  reviewDao.findReview(reviewDto.getMovieNo(), reviewDto.getReviewWriter());
		return isValid;
	}
	
	//리뷰등록+500포인트지급
	@PostMapping("/")
	public void insert(@RequestBody ReviewDto reviewDto) {
		int sequence = reviewDao.sequence();
		reviewDto.setReviewNo(sequence);
		reviewDao.insert(reviewDto); 
		reviewDao.updatePoint(reviewDto.getReviewWriter());
	}
	
	//영화별 리뷰 및 회원 정보
	@GetMapping("/{movieNo}")
	public List<UserReviewVO> reviewListByMovie(@PathVariable int movieNo, @RequestHeader("Authorization") String refreshToken) {
		List<UserReviewVO> list = reviewDao.listByMovie(movieNo);
		List<UserReviewVO> imageSetUpList = imageService.userReviewPhotoUrlSetUp(list);
//		return imageSetUpList;
		
		UserLoginVO loginVO = jwtService.parse(refreshToken);	
		String userId = loginVO.getUserId();

		//좋아요 수검색하는메서드 (imageSetUpList,userId)
		
		List<UserReviewVO> fin = likeService.check(imageSetUpList, userId);
		return fin;
	}
	
	//리뷰 삭제
	@DeleteMapping("/{reviewNo}")
	public void delete(@PathVariable int reviewNo){
		reviewDao.delete(reviewNo);
	}

	
}
