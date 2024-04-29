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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductRestController {

	@Autowired
	private ProductDao productDao;
	
	//문서용 설정 추가
	@Operation(
		description = "종류별 상품 조회",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "조회 성공",
				content = {
					@Content(
						mediaType = "application/json",
						array = @ArraySchema( 
							schema = @Schema(implementation = ProductDto.class)
						)
					)
				}
			),
			@ApiResponse(
				responseCode = "500",
				description ="서버 오류",
				content = {
					@Content(
						mediaType = "text/plain",
						schema = @Schema(implementation = String.class),
						examples = @ExampleObject("server error")
					)
				}
			)
		}
	)
	@GetMapping("/{productType}")
	public ResponseEntity<List<ProductDto>> find(@PathVariable String productType) {
		List<ProductDto> productDto = productDao.selectList(productType);
		
		if(productDto == null) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.status(200).body(productDto);
	}
}
