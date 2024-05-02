package com.kh.pheonix.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.CartDto;

@Repository
public class CartDao {

	@Autowired
	private SqlSession sqlSession;
	
	//등록
	public void insert(CartDto cartDto) {
		sqlSession.insert("cart.cartAdd", cartDto);
	}
	
	//회원별 장바구니 조회
	public List<CartDto> selectList(String userId){
		return sqlSession.selectList("cart.cartFind", userId);
	}
	
	//상품 조회
	public boolean findProductNo(int productNo) {
		CartDto result = sqlSession.selectOne("cart.productFind", productNo);
		
		if(result == null) {
			return false;
		}
		return true;
	}
	
	//상품 수량 업데이트
	public boolean updateQty(CartDto cartDto) {
		return sqlSession.update("cart.qtyEdit", cartDto) > 0;
	}
}
