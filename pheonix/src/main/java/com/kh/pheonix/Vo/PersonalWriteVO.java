package com.kh.pheonix.Vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PersonalWriteVO {
	private int personalNo;
	private String personalId;
	private String personalTitle;
	private String personalContent;
	private String personalWrite;
	private String personalEdit;
		
	private int writeNo;
	private String writeId;
	private String writeTitle;
	private String writeContent;
	private String writeDay;
	private String writeEdit;
		
}
