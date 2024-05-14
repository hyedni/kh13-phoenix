package com.kh.pheonix.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.Vo.ReserveStatsListVo;
import com.kh.pheonix.dao.ReserveStatsDao;

@CrossOrigin
@RestController
@RequestMapping("/stats")
public class ReserveStatsRestController {

	@Autowired
	private ReserveStatsDao reserveStatsDao;
	
	@GetMapping("/{reserveStatsDate}")
	public List<ReserveStatsListVo> list (@PathVariable String reserveStatsDate) {
		return reserveStatsDao.list(reserveStatsDate);
	}
	
}
