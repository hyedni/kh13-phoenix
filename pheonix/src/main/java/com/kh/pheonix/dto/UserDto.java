package com.kh.pheonix.dto;

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
	private String userJoin;
	private String userBirth;
	private String userEdit;
	private String userLogin;
	private int userPoint;
}
