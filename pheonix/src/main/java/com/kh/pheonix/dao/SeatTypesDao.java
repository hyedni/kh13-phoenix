package com.kh.pheonix.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.SeatTypesDto;

@Repository
public class SeatTypesDao {
	
	@Autowired
	private SqlSession sqlSession;

	public void insert(SeatTypesDto seatTypesDto) {
	
		sqlSession.insert("seatTypes.insert", seatTypesDto);
	}
	
	public int sequence() {
		
		return sqlSession.selectOne("seatTypes.sequence");
	}

}
