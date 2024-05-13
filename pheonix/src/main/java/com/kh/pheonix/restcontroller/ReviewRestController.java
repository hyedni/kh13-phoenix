package com.kh.pheonix.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.Vo.UserReviewVO;
import com.kh.pheonix.dao.ReviewDao;
import com.kh.pheonix.dto.ReviewDto;
import com.kh.pheonix.service.ImageService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/review")
public class ReviewRestController {
	
	@Autowired
	private ReviewDao reviewDao;
	
	@Autowired
	private ImageService imageService;
	
	//리뷰등록
	@PostMapping("/")
	public ResponseEntity<?> insert(@RequestBody ReviewDto reviewDto) {
		boolean isValid = reviewDao.findReview(reviewDto.getUserBookingNo());
		if(isValid) {
			int sequence = reviewDao.sequence();
			reviewDto.setReviewNo(sequence);
			reviewDao.insert(reviewDto); //관람한 영화에 대한 첫 리뷰인 경우에만 등록
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	//영화별 리뷰 및 회원 정보
	@GetMapping("/{movieNo}")
	public List<UserReviewVO> reviewListByMovie(@PathVariable int movieNo) {
		List<UserReviewVO> list = reviewDao.listByMovie(movieNo);
//		List<UserReviewVO> imageSetUpList = imageService.userReviewPhotoUrlSetUp(list);
//		return imageSetUpList;
		return list;
	}
	
	//리뷰 삭제
	@DeleteMapping("/{reviewNo}")
	public void delete(@PathVariable int reviewNo){
		reviewDao.delete(reviewNo);
	}

	
}
