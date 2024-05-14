package com.kh.pheonix.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.pheonix.dao.ReserveStatsDao;

@Service
public class ReserveStatsService {
	
	@Autowired
	private ReserveStatsDao reserveStatsDao;
	
	public List<Map<String, Object>> countsByMovie () {
		List<Integer> movieList = reserveStatsDao.movieNo();
	
		List<Map <String, Object>> resultList = new ArrayList<>();
		
		for (int movieNo : movieList) {
			Map <String, Object> results = new HashMap<>();
			//특정영화 예매건수
			int resertCntByMovie = reserveStatsDao.reserveCntByMovie(movieNo);
			results.put("movieNo", movieNo);
			results.put("cntByMovie", resertCntByMovie);
			resultList.add(results);
		}
		return resultList;
	}
	
	//전체 예매건수
	public int reserveCntAll () {
		return reserveStatsDao.reserveCntAll();
	}

	
}

