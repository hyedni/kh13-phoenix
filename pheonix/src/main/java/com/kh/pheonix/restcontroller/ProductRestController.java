package com.kh.pheonix.restcontroller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.pheonix.dao.ProductDao;
import com.kh.pheonix.dto.ProductDto;
import com.kh.pheonix.service.AttachService;
import com.kh.pheonix.service.ImageService;

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
	
	@Autowired
	private AttachService attachService;
	
	@Autowired
	private ImageService imageService;
	
	//상품 전체 조회
	@GetMapping("/")
	public List<ProductDto> list() {
		List<ProductDto> list = productDao.selectListAll();
		List<ProductDto> imageSetUpList = imageService.productPhotoUrlSetUp(list);//사진 주소 설정
		return imageSetUpList;
	}
	
	//상품 상세 조회
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
		List<ProductDto> urlDto = imageService.productPhotoUrlSetUp(productDto);
		if(productDto == null) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.status(200).body(urlDto);
	}
	
	@Operation(
		description = "상품 조회 상세",
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
	@GetMapping("/detail/{productNo}")
	public ResponseEntity<ProductDto> detail(@PathVariable int productNo) {
		ProductDto productDto = productDao.selectOne(productNo);
		ProductDto urlDto = imageService.productPhotoUrlbyOne(productDto);
		if(productDto == null) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.status(200).body(urlDto);
	}

	//상품 등록
	@PostMapping("/")
	public ProductDto insert(@RequestBody ProductDto productDto, @RequestParam MultipartFile attach) 
			throws IllegalStateException, IOException {
		int sequence = productDao.sequence();
		productDto.setProductNo(sequence);
		productDao.insert(productDto);//상품 테이블에 데이터 삽입
		
		//이미지 정보 삽입
		if(!attach.isEmpty()) {
			int attachNo = attachService.save(attach);
			productDao.connect(productDto.getProductNo(), attachNo);
		}
		return productDao.selectOne(sequence);
	}
	
	//상품 수정
	@PatchMapping("/")
	public ResponseEntity<?> edit (@RequestBody ProductDto productDto) {
		boolean result = productDao.edit(productDto);
		if (result == false) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
	
	//상품 삭제
	@DeleteMapping("/{productNo}")
	public ResponseEntity<?> delete(@PathVariable int productNo) {
		boolean result = productDao.delete(productNo);
		if(!result) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
}
