package com.kh.pheonix.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.PurchaseDto;

@Repository
public class PurchaseDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//구매완료 상품 등록
	public int sequence() {
		return sqlSession.selectOne("purchase.purchaseSequence");
	}
	public void insert(PurchaseDto purchaseDto) {
		sqlSession.insert("purchase.purchaseAdd", purchaseDto);
	}
	
}
