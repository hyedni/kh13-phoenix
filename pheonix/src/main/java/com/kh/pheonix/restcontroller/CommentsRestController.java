package com.kh.pheonix.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.dao.CommentsDao;
import com.kh.pheonix.dto.CommentsDto;

@CrossOrigin
@RestController
@RequestMapping("/comments")
public class CommentsRestController {

	@Autowired
	private CommentsDao commentsDao;

	@GetMapping("/")//조회
	public List<CommentsDto> list() {
		return commentsDao.selectList();
	}
	
	@PostMapping("/")//등록
	public void insert(@RequestBody CommentsDto commentsDto) {
		int sequence = commentsDao.sequence();
		commentsDto.setCommentsId(sequence);
		System.out.println(commentsDto);
		commentsDao.insert(commentsDto);
	}
	
	@PutMapping("/")//전체수정
	public boolean edit(@RequestBody CommentsDto commentsDto) {
		boolean result = commentsDao.update(commentsDto);
		return result;
	}
	
	@DeleteMapping("/{commentsId}")//삭제
	public boolean delete(@PathVariable int commentsId) {
		return commentsDao.delete(commentsId);
	}
	
	

}
