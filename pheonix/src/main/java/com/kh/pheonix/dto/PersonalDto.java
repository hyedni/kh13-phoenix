package com.kh.pheonix.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PersonalDto {
	private int personalNo;
	private String persnalId;
	private String personalTitle;
	private String personalContent;
	private Date personalWrite;
	private Date personalEdit;
	private String personalDelete;
	

}