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
}
