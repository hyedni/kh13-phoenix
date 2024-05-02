package com.kh.pheonix.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.ProductDto;

@Repository
public class ProductDao {

	@Autowired
	private SqlSession sqlSession;
	
	//종류별 조회
	public List<ProductDto> selectListAll(){
		return sqlSession.selectList("product.productList");
	}
	
	public List<ProductDto> selectList(String productType) {
		return sqlSession.selectList("product.productFind", productType);
	}
	//상세 조회
	public ProductDto selectOne(int productNo) {
		return sqlSession.selectOne("product.productDetail", productNo);
	}
	
	//상품 등록
	public int sequence() {
		return sqlSession.selectOne("product.sequence");
	}
	public void insert(ProductDto productDto) {
		sqlSession.insert("product.insert", productDto);
	}
	
	//상품 수정
	public boolean edit(ProductDto productDto) {
		return sqlSession.update("product.edit", productDto) > 0;
	}
	
	//상품 삭제
	//이미지도 같이 삭제되도록
	public boolean delete(int productNo) {
		return sqlSession.delete("product.delete", productNo) > 0;
	}
	
	//연결테이블에 데이터 삽입
	public void connect(int productNo, int attachNo) {
		Map<String, Object> data = new HashMap<>();
		data.put("productNo", productNo);
		data.put("attachNo", attachNo);
		sqlSession.insert("product.connect", data);
	}
	
	//attach 테이블의 번호 찾기
	public int findAttach (int productNo) {
		return sqlSession.selectOne("product.findAttach", productNo);
	}

	
}