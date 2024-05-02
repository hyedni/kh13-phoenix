package com.kh.pheonix.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.CinemaDto;

@Repository
public class CinemaDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int sequence () {
		return sqlSession.selectOne("cinema.sequence");
	}
	
	public void insert (CinemaDto cinemaDto) {
		sqlSession.insert("cinema.insert", cinemaDto);
	}
	
	public List<String> names (String cinemaRegion) {
		return sqlSession.selectList("cinema.findName", cinemaRegion);
	}
	
	public List<CinemaDto> list () {
		return sqlSession.selectList("cinema.list");
	}
	
	public CinemaDto find (String cinemaName) {
		return sqlSession.selectOne("cinema.find", cinemaName);
	}
	
	public CinemaDto findByNo (int cinemaNo) {
		return sqlSession.selectOne("cinema.findByNo", cinemaNo);
	}
	
	public boolean edit (CinemaDto cinemaDto) {
		return sqlSession.update("cinema.edit", cinemaDto) > 0;
	}
	
	public boolean delete (int cinemaNo) {
		return sqlSession.delete("cinema.delete", cinemaNo) > 0;
	}

	
	
}
