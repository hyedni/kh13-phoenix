package com.kh.pheonix.restcontroller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.pheonix.dao.MovieDao;
import com.kh.pheonix.dto.MovieDto;
import com.kh.pheonix.service.AttachService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/movie")
public class MovieRestController {

	@Autowired
	private MovieDao movieDao;
	
	@Autowired
	private AttachService attachService;
	
	//전체조회
	@GetMapping("/")
	public List<MovieDto> list () {
		return movieDao.list();
	}
	
	//등록
	@PostMapping("/")
	public MovieDto insert (@RequestBody MovieDto movieDto, @RequestParam MultipartFile attach) throws IllegalStateException, IOException {
		int sequence = movieDao.sequence();
		movieDto.setMovieNo(sequence);
		movieDao.insert(movieDto);
		
		if (!attach.isEmpty()) {
			int attachNo = attachService.save(attach);
			movieDao.connect(movieDto.getMovieNo(), attachNo);
		}
		return movieDao.find(sequence);
	}
	
	//1건조회
	@GetMapping("/{movieNo}")
	public ResponseEntity<MovieDto> find (@PathVariable int movieNo) {
		MovieDto movieDto = movieDao.find(movieNo);
		if (movieDto == null) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().body(movieDto);
	}
	
	//수정
	@PatchMapping("/")
	public ResponseEntity<?> edit (@RequestBody MovieDto movieDto) {
		boolean result = movieDao.edit(movieDto);
		if (result == false) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
	
	//삭제
	@DeleteMapping("/{movieNo}")
	public ResponseEntity<?> delete (@PathVariable int movieNo) {
		boolean result = movieDao.delete(movieNo);
		if (result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/image/{movieNo}")
	public ResponseEntity<Void> imageApply(@PathVariable int movieNo) {
	    try {
	        int attachNo = movieDao.findAttach(movieNo);
	        return ResponseEntity.status(HttpStatus.FOUND)
	                             .location(URI.create("/download?attachNo=" + attachNo))
	                             .build();
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.FOUND) 
	        						.location(URI.create("/image/attachNull.png"))
	                             .build();
	    }
	}

}





