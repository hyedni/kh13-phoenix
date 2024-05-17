package com.kh.pheonix.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.Vo.ReservationHistory;
import com.kh.pheonix.dto.ReservationDetailDto;
import com.kh.pheonix.dto.SeatReservationInfo;

@CrossOrigin
@RestController
@RequestMapping("/mypage")
public class ReservationHistoryController {

	/*@Autowired
	private HistoryDao historyDao;
	
	@GetMapping("/history/{userId}")
	public ReservationHistory reservationHistory(@PathVariable String userId) {
		ReservationHistory reservationHistory = new ReservationHistory();
		
		ReservationDetailDto reservationDetailDto = new ReservationDetailDto();
		List<SeatReservationInfo> list = new ArrayList<>();
		
		reservationDetailDto.set
		
		return reservationHistory;*/
//	}
}
