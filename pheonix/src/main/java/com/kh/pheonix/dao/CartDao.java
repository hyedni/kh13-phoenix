package com.kh.pheonix.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.CartDto;
import com.kh.pheonix.dto.ProductDto;
import com.kh.pheonix.dto.UserDto;
import com.kh.pheonix.vo.CartProductVO;

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
	
	//장바구니에 있는 상품 조회
	public boolean findProductNo(int productNo) {
		CartDto result = sqlSession.selectOne("cart.productFind", productNo);
		
		if(result == null) {
			return false;
		}
		return true;
	}
	
	//로그인한 사용자의 카트+상품 조회
	public List<CartProductVO> cartProductList(String userId) {
		return sqlSession.selectList("cart.joinCartProduct", userId);	
	}
	
	//상품에 대한 정보 조회
	public List<ProductDto> findProductInfo(int cartProductNo) {
		return sqlSession.selectList("cart.productInfo", cartProductNo);
	}
	
	//회원정보 검색
	public UserDto selectOneUser(String userId) {
		return sqlSession.selectOne("cart.findUser", userId);
	}
	
	//상품 수량 업데이트
	public boolean updateQty(CartDto cartDto) {
		return sqlSession.update("cart.qtyEdit", cartDto) > 0;
	}
	public boolean updateQty(CartProductVO cartProductVO) {
		return sqlSession.update("cart.qtyCartEdit", cartProductVO) > 0;
	}
	
	
	//장바구니 내용 삭제(단일)
	public boolean delete(int productNo, String userId) {
		Map<String, Object> info = new HashMap<>();
		info.put("cartProductNo", productNo);
		info.put("cartUserId", userId);
		return sqlSession.delete("cart.delete", info) > 0;
	}
	
	//장바구니 조회 시 30일 지난 데이터 삭제
	public boolean delete(String userId) {
		return sqlSession.delete("cart.deleteByTime", userId) > 0;
	} 
	
	
	
}
