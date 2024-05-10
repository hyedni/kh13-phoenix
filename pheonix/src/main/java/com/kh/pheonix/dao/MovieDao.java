package com.kh.pheonix.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.MovieDto;

@Repository
public class MovieDao {

	@Autowired
	private SqlSession sqlSession;
	
	public int sequence () {
		return sqlSession.selectOne("movie.sequence");
	}
	public void insert (MovieDto movieDto) {
		sqlSession.insert("movie.insert", movieDto);
	}
	
	public List<MovieDto> list () {
		return sqlSession.selectList("movie.list");
	}
	
	public List<MovieDto> onList() {
		return sqlSession.selectList("movie.onList");
	}
	
	public List<MovieDto> allList() {
		return sqlSession.selectList("movie.allList");
	}
	
	public MovieDto find (int movieNo) {
		return sqlSession.selectOne("movie.find", movieNo);
	}
	
	public boolean edit (MovieDto movieDto) {
		return sqlSession.update("movie.edit", movieDto) > 0;
	}
	
	public boolean delete (int movieNo) {
		return sqlSession.delete("movie.delete", movieNo) > 0;
	}
	
	public void connect (int movieNo, int attachNo) {
		Map<String, Object> data = new HashMap<>();
		data.put("movieNo", movieNo);
		data.put("attachNo", attachNo);
		sqlSession.insert("movie.connect", data);
	}
	
	public int findAttach (int movieNo) {
		return sqlSession.selectOne("movie.findAttach", movieNo);
	}
	
	public void updateMovieOn (String movieOpenDate) {
		sqlSession.update("movie.updateMovieOn", movieOpenDate);
	}
}








