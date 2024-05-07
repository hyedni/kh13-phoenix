package com.kh.pheonix.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.TheaterDto;

@Repository
public class TheaterDao {

	@Autowired
	private SqlSession sqlSession;
	
	public int sequence() {
		return sqlSession.selectOne("theater.sequence");
	}
	
	public void insert(TheaterDto theaterDto) {
		sqlSession.insert("theater.insert");
	}
	
	public boolean delete(int theaterNo) {
		return sqlSession.delete("theater.delete") > 0;
	}
	
	public List<TheaterDto> list () {
		return sqlSession.selectList("theater.list");
	}
	
	public List<TheaterDto> find (int cinemaNo) {
		return sqlSession.selectList("theater.find", cinemaNo);
	}
	
	public boolean edit (TheaterDto theaterDto) {
		return sqlSession.update("theater.update", theaterDto) > 0;
	}
}




