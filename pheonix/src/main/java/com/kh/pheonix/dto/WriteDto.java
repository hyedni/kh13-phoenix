package com.kh.pheonix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class WriteDto {
	private int writeNo;
	private int personalNo;
	private String writeId;
	private String writeTitle;
	private String writeContent;
	private String writeDay;
	private String writeEdit;
	
	
	
}
