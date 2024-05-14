package com.kh.pheonix.Vo;

import java.util.List;

import com.kh.pheonix.dto.SeatStatus;
import com.kh.pheonix.dto.SeatTypesDto;
import com.kh.pheonix.dto.TheaterDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class TheaterPayLoadVo {
	
	private List<SeatTypesDto> seats;
    private TheaterDto theater;

}
