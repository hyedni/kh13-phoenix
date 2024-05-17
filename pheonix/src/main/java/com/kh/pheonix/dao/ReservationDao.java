package com.kh.pheonix.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.ReservationDetailDto;
import com.kh.pheonix.dto.ReservationDto;
import com.kh.pheonix.dto.SeatReservationDto;

@Repository
public class ReservationDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int sequence() {
		return sqlSession.selectOne("reservation.sequence");
	}
	
	public int ticketPriceCalculator(int seatTypesNo, int movieScheduleNo, String memberType) {
		
		Map<String, Object> params = new HashMap<>();
        params.put("seatTypesNo", seatTypesNo);
        params.put("movieScheduleNo", movieScheduleNo);
        params.put("memberType", memberType);
        
        int price = sqlSession.selectOne("reservation.ticketPriceCalculator", params);
		return price;
	}
	
	public void insert(ReservationDto dto) {
		
		sqlSession.insert("reservation.insert", dto);
	}
	
	public void insertSeat(SeatReservationDto dto) {
		
		sqlSession.insert("reservation.insertSeat", dto);
	}
	
	
//	데이터 조회
	public List<ReservationDetailDto> selectList(String userId) {
		return sqlSession.selectList("reservation.reservationList", userId);
	}
	
	public ReservationDetailDto selectOne(String userId, int reservationNo) {
		Map<String, Object> data = new HashMap<>();
		data.put("userId", userId);
		data.put("reservationNo", reservationNo);
		return sqlSession.selectOne("reservation.reservationDetail", data);
	}

	

}
