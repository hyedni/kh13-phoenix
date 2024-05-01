package com.kh.pheonix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.pheonix.dao.MovieDao;
import com.kh.pheonix.dto.MovieDto;

@Service
public class ImageService {

	@Autowired
	private MovieDao movieDao;

	//이미지에 url주소 부여 
	public String getMovieImgLink(int movieNo) {
		try {
			int attachNo = movieDao.findAttach(movieNo);
			return "http://localhost:8080/download?attachNo=" + attachNo;
		} catch (Exception e) {
			return "http://localhost:8080/image/attachNull.png";
		}
	}

	//전체조회시 이미지 출력하기
	public List<MovieDto> moviePhotoUrlSetUp(List<MovieDto> movieDto) {
		List<MovieDto> list = movieDto;

		for (MovieDto dto : list) {
			String movieImgLink = getMovieImgLink(dto.getMovieNo());
			dto.setMovieImgLink(movieImgLink);
		}
		return list;
	}
	
	//1건 상세조회시 해당 이미지 출력하기
	public MovieDto moviePhotoUrlbyOne (MovieDto movieDto) {
		MovieDto dto = movieDto;
		String movieImgLink = getMovieImgLink(movieDto.getMovieNo());
		dto.setMovieImgLink(movieImgLink);
		return dto;
	}

}
