package com.kh.pheonix.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.pheonix.dto.ChatbotDto;

@Repository
public class ChatbotDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<ChatbotDto> selectList() {
		return sqlSession.selectList("chatbot.list");
	}
	public ChatbotDto selectOne(int chatbotNo) {
		return sqlSession.selectOne("chatbot.find", chatbotNo);
	}
}
