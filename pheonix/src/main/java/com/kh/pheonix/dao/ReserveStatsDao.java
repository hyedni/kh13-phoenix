package com.kh.pheonix.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.ReserveStatsDto;
import com.kh.pheonix.vo.ReserveStatsListVo;

@Repository
public class ReserveStatsDao {

	@Autowired
	private SqlSession sqlSession;
	
	//상영중인 영화번호들 
	public List<Integer> movieNo () {
		return sqlSession.selectList("reserveStats.movieNo");
	}
	
	//특정영화 예매건수
	public int reserveCntByMovie (int movieNo) {
		return sqlSession.selectOne("reserveStats.reserveCntByMovie", movieNo);
	}
	
	//전체 예매건수
	public int reserveCntAll () {
		return sqlSession.selectOne("reserveStats.reserveCntAll");
	}
	
	//집계결과 등록
	public void insert (ReserveStatsDto dto) {
		sqlSession.insert("reserveStats.insert", dto);
	}	
	
	public int sequence () {
		return sqlSession.selectOne("reserveStats.sequence");
	}
	
	public List<ReserveStatsListVo> list (String reserveStatsDate) {
		return sqlSession.selectList("reserveStats.list", reserveStatsDate);
	}
}
