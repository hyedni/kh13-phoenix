package com.kh.pheonix.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.Vo.MovieScheduleVo;
import com.kh.pheonix.dto.MovieScheduleDto;

@Repository
public class MovieScheduleDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<MovieScheduleVo> list (MovieScheduleVo vo) {
		return sqlSession.selectList("movieSchedule.list", vo);
	}
	
	public List<Map<String, Object>> movieList () {
		return sqlSession.selectList("movieSchedule.movieList");
	}
	
	public List<Map<String, Object>> cinemaList () {
		return sqlSession.selectList("movieSchedule.cinemaList");
	}

	public List<Map<String, Object>> theaterList (int cinemaNo) {
		return sqlSession.selectList("movieSchedule.theaterList", cinemaNo);
	}
	
	public int seats (int theaterNo) {
		return sqlSession.selectOne("movieSchedule.seats", theaterNo);
	}
	
	public int seq () {
		return sqlSession.selectOne("movieSchedule.seq");
	}
	
	public void insert (MovieScheduleDto dto) {
		sqlSession.insert("movieSchedule.insert", dto);
	}
	
	public int getRunningTime (int movieNo) {
		return sqlSession.selectOne("movieSchedule.getRunningTime", movieNo);
	}
	
	public boolean delete (int movieScheduleNo) {
		return sqlSession.delete("movieSchedule.delete", movieScheduleNo) > 0;
	}
	
	public boolean edit (MovieScheduleDto dto) {
		return sqlSession.update("movieSchedule.edit", dto) > 0;
	}
	
	public MovieScheduleDto find (int movieScheduleNo) {
		return sqlSession.selectOne("movieSchedule.find", movieScheduleNo);
	}
	
	public MovieScheduleVo scheduleFind (int movieScheduleNo) {
		return sqlSession.selectOne("movieSchedule.scheduleFind", movieScheduleNo);
	}
	
}
