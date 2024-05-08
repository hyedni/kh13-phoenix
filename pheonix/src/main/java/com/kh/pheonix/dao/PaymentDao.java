package com.kh.pheonix.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.PaymentDetailDto;
import com.kh.pheonix.dto.PaymentDto;

@Repository
public class PaymentDao {
	@Autowired
	private SqlSession sqlSession;
	
	//결제 등록과 관련된 메소드
	public int paymentSequence() {
		return sqlSession.selectOne("payment.paymentSequence");
	}
	public void insertPayment(PaymentDto paymentDto) {
		sqlSession.insert("payment.paymentAdd", paymentDto);
	}
	
	//결제 상세와 관련된 메소드
	public int paymentDetailSequence() {
		return sqlSession.selectOne("payment.paymentDetailSequence");
	}
	public void insertPaymentDetail(PaymentDetailDto paymentDetailDto) {
		sqlSession.insert("payment.paymentDetailAdd", paymentDetailDto);
	}
	
	
	
	//주문조회와 관련된 메소드
	public List<PaymentDto> paymentList(){
		return sqlSession.selectList("payment.paymentList");
	}
	public List<PaymentDetailDto> paymentDetailList(int paymentNo){
		return sqlSession.selectList("payment.paymentDetailList", paymentNo);
	}
	
	//단일조회
	public PaymentDto selectOne(int paymentNo) {
		return sqlSession.selectOne("payment.paymentFind", paymentNo);
	}
	//상세조회
	public PaymentDetailDto paymentDetailFind(int paymentDetailNo) {
		return sqlSession.selectOne("payment.paymentDetailFind", paymentDetailNo);
	}
	
	//잔여금액 차감 (변경)
	public boolean paymentRemainDecrease(int paymentNo, int amount) {
		Map<String, Object> data = new HashMap<>();
		data.put("paymentNo", paymentNo);
		data.put("amount", amount);
		return sqlSession.update("payment.paymentRemainDecrease", data) > 0;
	}
	//상태 변경 '취소'
	public boolean paymentDetailCancel(int paymentDetailNo) {
		return sqlSession.update("payment.paymentDetailCancel", paymentDetailNo) > 0;
	}
	public boolean paymentDetailCancelAll(int paymentNo) {
		return sqlSession.update("payment.paymentDetailCancelAll", paymentNo) > 0;
	}
}
