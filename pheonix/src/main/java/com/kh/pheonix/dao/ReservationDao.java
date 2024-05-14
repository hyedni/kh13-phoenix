package com.kh.pheonix.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

}
