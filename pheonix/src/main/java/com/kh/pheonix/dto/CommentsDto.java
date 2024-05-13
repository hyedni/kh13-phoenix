package com.kh.pheonix.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CommentsDto {
	private int commentsId;
	private int personalNo;
	private String commentsContent;
	private String commentsWriter;
	private String commentsWritten;

	
}
