package com.kh.pheonix.vo;

import java.util.List;

import com.kh.pheonix.dto.ReservationDetailDto;
import com.kh.pheonix.dto.SeatReservationInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReservationHistory {

	private List<SeatReservationInfo> seatReservationInfo;
	private ReservationDetailDto reservationDetailDto;
}
