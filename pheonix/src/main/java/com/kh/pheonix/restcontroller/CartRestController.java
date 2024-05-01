package com.kh.pheonix.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.dao.CartDao;
import com.kh.pheonix.dto.CartDto;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartRestController {
	
	@Autowired
	private CartDao cartDao;
	
	//등록 - 장바구니 담기
	@PostMapping("/add/")
	public List<CartDto> insert(@RequestBody CartDto cartDto) {
		boolean isValid = cartDao.findProductNo(cartDto.getCartProductNo());
		if(isValid) { //장바구니에 기존 상품이 있으면
			cartDao.updateQty(cartDto);
		}
		else {
			cartDao.insert(cartDto);//장바구니 담기			
		}
		return cartDao.selectList(cartDto.getCartUserId());
	}
}
