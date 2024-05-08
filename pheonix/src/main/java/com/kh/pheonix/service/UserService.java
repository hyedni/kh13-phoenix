package com.kh.pheonix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.pheonix.dao.SocialUserDao;
import com.kh.pheonix.dao.UserDao;
import com.kh.pheonix.dto.UserDto;

@Service
public class UserService {
    
    @Autowired
    private UserDao userDao;
    @Autowired
    private SocialUserDao socialUserDao;

	public boolean registerUser(UserDto userDto) {
		// 필수 항목이 누락되었는지 확인
		 if (userDto.getUserId() == null ||
		            userDto.getUserPw() == null ||
		            userDto.getUserEmail() == null ||
		            userDto.getUserName() == null) {
		            // 필수 항목이 누락된 경우 회원가입 실패
		            return false;
		        }
        
        // 사용자가 이미 존재하는지 확인
        if (userDao.selectOne(userDto.getUserId()) != null) {
            // 이미 존재하는 경우 회원가입 실패
            return false;
        }
        
        // 사용자가 존재하지 않는 경우, 새로운 사용자를 등록
        userDao.insert(userDto);
        
        return true;
    }

    
}
