package com.kh.pheonix.websocket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.pheonix.dao.ChatbotDao;
import com.kh.pheonix.dto.ChatbotDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChatbotWebSocketServer extends TextWebSocketHandler {

	@Autowired
	private ChatbotDao chatbotDao;

		@Override
		public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		//목록 조회
		List<ChatbotDto> list = chatbotDao.selectList();
		
		//목록으로 JSON문자열 생성(수동)
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(list);
		
		//메세지 객체 생성
		TextMessage message = new TextMessage(json);
		
		//전송
		session.sendMessage(message);
		}
		
		@Override
		protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
			int chatbotNo = Integer.parseInt(message.getPayload());
			ChatbotDto chatbotDto = chatbotDao.selectOne(chatbotNo);
					
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(chatbotDto);
			TextMessage response = new TextMessage(json); //포장
			
			session.sendMessage(response);
		}
		
}