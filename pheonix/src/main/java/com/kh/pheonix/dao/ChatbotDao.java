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
	
	//시퀀스
	public int sequence() {
		return sqlSession.selectOne("chatbot.sequence");
	}
	//등록
	public void insert(ChatbotDto chatbotDto) {
		sqlSession.insert("chatbot.insert", chatbotDto);
	}
	//조회
	public List<ChatbotDto> selectList(){
		return sqlSession.selectList("chatbot.list");
	}
	//상세
	public ChatbotDto selectOne(int chatbotNo) {
		return sqlSession.selectOne("chatbot.find",chatbotNo);
	}
	//전체 수정
	public boolean editAll(ChatbotDto chatbotDto) {
		return sqlSession.update("chatbot.editAll", chatbotDto) > 0;
	}
	//일부 수정
	public boolean edit(ChatbotDto chatbotDto) {
		return sqlSession.update("chatbot.edit", chatbotDto) > 0;
	}
	//삭제
	public boolean delete(int chatbotNo) {
		return sqlSession.delete("chatbot.delete", chatbotNo) > 0;
	}
}