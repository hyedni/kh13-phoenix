package com.kh.pheonix.restcontroller;

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

import com.kh.pheonix.Vo.CartProductVO;
import com.kh.pheonix.dao.CartDao;
import com.kh.pheonix.dao.UserDao;
import com.kh.pheonix.dto.CartDto;
import com.kh.pheonix.dto.ProductDto;
import com.kh.pheonix.dto.UserDto;
import com.kh.pheonix.service.ImageService;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartRestController {
	
	@Autowired
	private CartDao cartDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ImageService imageService;
	
	//등록 - 장바구니 담기
	@PostMapping("/add/")
	public void insert(@RequestBody CartDto cartDto) {
		boolean isValid = cartDao.findProductNo(cartDto.getCartProductNo());
		if(isValid) { //장바구니에 기존 상품이 있으면
			cartDao.updateQty(cartDto);
		}
		else {
			cartDao.insert(cartDto);//장바구니 담기			
		}	
	}
	
	//회원별 조회
	@GetMapping("/list/{userId}") 
	public List<CartDto> listByUserId(@PathVariable String userId){
		return cartDao.selectList(userId);
	}
	
	//상품정보 조회
	@GetMapping("/product/{cartProductNo}")
	public List<ProductDto> productList(@PathVariable int cartProductNo) {
		return cartDao.findProductInfo(cartProductNo);
	}
	
	//장바구니 + 상품 정보 조회
	@GetMapping("/combine/{CartUserId}")
	public List<CartProductVO> cartProductList(@PathVariable String CartUserId) {
		List<CartProductVO> list = cartDao.cartProductList(CartUserId);
		List<CartProductVO> imageSetUpList = imageService.cartProductPhotoUrlSetUp(list);
		return imageSetUpList;
	}
	
	//회원 테이블의 회원 정보 조회
	@GetMapping("/user/{userId}")
	public UserDto complex(@PathVariable String userId) {
		return cartDao.selectOneUser(userId);
	}
	
	//상품 수정
	@PatchMapping("/")
	public ResponseEntity<?> edit(@RequestBody CartDto cartDto) {
		boolean result = cartDao.updateQty(cartDto);
		if (result == false) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
	@PatchMapping("/combine")
	public ResponseEntity<?> cartProductQtyEdit(@RequestBody CartProductVO cartProductVO) {
		boolean result = cartDao.updateQty(cartProductVO);
		if (result == false) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
	
	//상품 삭제
	@DeleteMapping("/")
	public ResponseEntity<?> delete(@RequestParam String userId, @RequestParam int productNo){
		boolean result = cartDao.delete(productNo, userId);
		if(result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
	
	//30일이 지난 값 삭제 
	@DeleteMapping("/deleteByTime/{userId}")
	public ResponseEntity<?> deleteByTime(@PathVariable String userId){
		boolean result = cartDao.delete(userId);
		if(result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
}
