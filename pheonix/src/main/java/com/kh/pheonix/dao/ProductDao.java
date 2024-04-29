package com.kh.pheonix.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.ProductDto;

@Repository
public class ProductDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<ProductDto> selectList(String productType) {
		return sqlSession.selectList("product.productFind", productType);
	}
}
