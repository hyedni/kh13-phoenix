package com.kh.pheonix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.pheonix.dao.LostDao;
import com.kh.pheonix.dao.MovieDao;
import com.kh.pheonix.dao.ProductDao;
import com.kh.pheonix.dto.LostDto;
import com.kh.pheonix.dto.MovieDto;
import com.kh.pheonix.dto.ProductDto;

@Service
public class ImageService {

	@Autowired
	private MovieDao movieDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private LostDao lostDao;
	
	//영화
	//이미지에 url주소 부여 
	public String getMovieImgLink(int movieNo) {
		try {
			int attachNo = movieDao.findAttach(movieNo);
			return "http://localhost:8080/download?attachNo=" + attachNo;
		} catch (Exception e) {
			return "http://localhost:8080/image/attachNull.png";
		}
	}
	
	//전체조회시 영화 이미지 출력하기
	public List<MovieDto> moviePhotoUrlSetUp(List<MovieDto> movieDto) {
		List<MovieDto> list = movieDto;

		for (MovieDto dto : list) {
			String movieImgLink = getMovieImgLink(dto.getMovieNo());
			dto.setMovieImgLink(movieImgLink);
		}
		return list;
	}
	
	//1건 상세조회시 해당 영화 이미지 출력하기
	public MovieDto moviePhotoUrlbyOne (MovieDto movieDto) {
		MovieDto dto = movieDto;
		String movieImgLink = getMovieImgLink(movieDto.getMovieNo());
		dto.setMovieImgLink(movieImgLink);
		return dto;
	}
	
	//스토어
	//이미지에 url 주소 부여
	public String getProductImgLink(int productNo) {
		try {
			int attachNo = productDao.findAttach(productNo);
			return "http://localhost:8080/download?attachNo=" + attachNo;
		} catch (Exception e) {
			return "http://localhost:8080/image/productNullImg.png";
		}
	}
	
	//전체 조회 시 상품 이미지 출력
	public List<ProductDto> productPhotoUrlSetUp(List<ProductDto> productDto) {
		List<ProductDto> list = productDto;
		
		for (ProductDto dto : list) {
			String productImgLink = getProductImgLink(dto.getProductNo());
			dto.setProductImgLink(productImgLink);
		}
		return list;
	} 
	//1건 상세 조회 시 해당 상품 이미지 출력 (이미지 단일 조회)
	public ProductDto productPhotoUrlbyOne(ProductDto productDto) {
		ProductDto dto = productDto;
		String productImgLink = getProductImgLink(dto.getProductNo());
		dto.setProductImgLink(productImgLink);
		return dto;
	}
	
	//분실물
	//이미지에 url주소부여
	public String getLostImgLink(int lostNo) {
		try {
			int attachNo = lostDao.findAttach(lostNo);
			return "http://localhost:8080/download?attachNo=" + attachNo;
		} catch (Exception e) {
			return "http://localhost:8080/image/lostNullImg.png";
		}
	}
	
	//전체 조회 시 상품 이미지 출력
	public List<LostDto> lostPhotoUrlSetUp(List<LostDto> lostDto) {
		List<LostDto> list = lostDto;
		
		for (LostDto dto : list) {
			String lostImgLink = getLostImgLink(dto.getLostNo());
			dto.setLostImgLink(lostImgLink);
		}
		return list;
	} 
	
	//1건 상세 조회 시 해당 상품 이미지 출력 (이미지 단일 조회)
		public LostDto lostPhotoUrlbyOne(LostDto lostDto) {
			LostDto dto = lostDto;
			String lostImgLink = getLostImgLink(dto.getLostNo());
			dto.setLostImgLink(lostImgLink);
			return dto;
		}
}
