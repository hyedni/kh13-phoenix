package com.kh.pheonix.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class UserDto {
	private String userId;
	private String userPw;
	private String userNick;
	private String userName;
	private String userContact;
	private String userGrade;
	private String userEmail;
	private Date userJoin;
	private Date userBirth;
	private Date userEdit;
	private Date userLogin;
	private int userPoint;
}
