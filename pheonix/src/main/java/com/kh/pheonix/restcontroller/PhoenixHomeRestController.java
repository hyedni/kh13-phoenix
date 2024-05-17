package com.kh.pheonix.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.dao.ProductDao;
import com.kh.pheonix.dto.ProductDto;
import com.kh.pheonix.service.AttachService;
import com.kh.pheonix.service.ImageService;

@RestController
@CrossOrigin
@RequestMapping("/home")
public class PhoenixHomeRestController {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private AttachService attachService;
	
	@Autowired
	private ImageService imageService;
	
	@GetMapping("/{productType}")
	public ResponseEntity<List<ProductDto>> listbyHome(@PathVariable String productType) {
		List<ProductDto> productDto = productDao.listbyHome(productType);
		List<ProductDto> urlDto = imageService.productPhotoUrlSetUp(productDto);
		if(productDto == null) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.status(200).body(urlDto);
	}

}
