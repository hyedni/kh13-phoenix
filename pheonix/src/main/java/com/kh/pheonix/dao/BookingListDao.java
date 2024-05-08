package com.kh.pheonix.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.Vo.BookingListByDateVo;
import com.kh.pheonix.Vo.BookingListVo;
import com.kh.pheonix.Vo.MovieListVo;

@Repository
public class BookingListDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<BookingListVo> listAll () {
		return sqlSession.selectList("bookingList.listAll");
	}
	
	public List<BookingListVo> listByMovie (int movieNo) {
		return sqlSession.selectList("bookingList.listByMovie", movieNo);
	}
	
	public List<BookingListVo> listByCinema (int movieNo, String cinemaName) {
		Map <String, Object> data = new HashMap<>();
		data.put("movieNo", movieNo);
		data.put("cinemaName", cinemaName);
		return sqlSession.selectList("bookingList.listByCinema", data);
	}
	
	public List<BookingListVo> listByDate (int movieNo, String cinemaName, String startDate) {
		Map <String, Object> data = new HashMap<>();
		data.put("movieNo", movieNo);
		data.put("cinemaName", cinemaName);
		data.put("startDate", startDate);
		return sqlSession.selectList("bookingList.listByDate", data);
	}
	
	public List<MovieListVo> movieTitle () {
		return sqlSession.selectList("bookingList.movieTitle");
	}
	
	public List<String> theaterList (int movieNo) {
		return sqlSession.selectList("bookingList.theaterList", movieNo);
	}
	
}















