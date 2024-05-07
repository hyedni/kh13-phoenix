package com.kh.pheonix.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.dao.ChatbotDao;
import com.kh.pheonix.dto.ChatbotDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

//문서용 설정 추가
@Tag(name = "챗봇정보 관리도구", description = "chatbot 테이블 CRUD 처리")

@CrossOrigin
@RestController
@RequestMapping("/chatbotEdit")
public class ChatbotRestController {

	@Autowired
	private ChatbotDao chatbotDao;
	
	//등록
	@Operation(
		description = "챗봇 정보 등록",
		responses = {
			@ApiResponse(responseCode = "200",description = "챗봇 등록 완료",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = ChatbotDto.class)
				)
			),
			@ApiResponse(responseCode = "500",description = "서버 오류",
				content = @Content(
						mediaType = "text/plain",
						schema = @Schema(implementation = String.class), 
						examples = @ExampleObject("server error")
				)
			)
		}
	)
	@PostMapping("/")
	public ChatbotDto insert(@RequestBody ChatbotDto chatbotDto) {
//		int sequence = chatbotDao.sequence();//번호생성
//		chatbotDto.setChatbotNo(sequence);//번호설정
		chatbotDao.insert(chatbotDto);//등록
		return chatbotDao.selectOne(chatbotDto.getChatbotNo());//등록된 결과를 조회하여 반환
	}
	
	//목록
	@Operation(
		description = "챗봇 목록 조회",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "조회 성공",
				content =@Content(
						mediaType = "application/json",
						array = @ArraySchema(schema = @Schema(implementation = ChatbotDto.class))
				)
			),
			@ApiResponse(
				responseCode = "500",
				description = "서버 오류",
				content = @Content(
						mediaType = "text/plain",
						schema = @Schema(implementation = String.class),
						examples = @ExampleObject("server error")
				)
			)
		}
	)
	@GetMapping("/")
	public List<ChatbotDto> list() {
		return chatbotDao.selectList();
	}
	
	
	//상세
	@Operation(
		description = "챗봇 상세 조회",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "조회 성공",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = ChatbotDto.class)
				)
			),
			@ApiResponse(
				responseCode = "404", description = "해당 챗봇의 데이터가 없음",
				content = @Content(
					mediaType = "text/plain", 
					schema = @Schema(implementation = String.class),
					examples = @ExampleObject("not found")
				)
			),
			@ApiResponse(
				responseCode = "500", description = "서버 오류",
				content = @Content(
					mediaType = "text/plain", 
					schema = @Schema(implementation = String.class),
					examples = @ExampleObject("server error")
				)
			)
		}
	)
	@GetMapping("/{chatbotNo}")
	public ResponseEntity<ChatbotDto> find(@PathVariable int chatbotNo) {
		ChatbotDto chatbotDto = chatbotDao.selectOne(chatbotNo);
		if(chatbotDto == null) {
			//return ResponseEntity.notFound().build();
			return ResponseEntity.status(404).build();
		}
		//return ResponseEntity.ok(chatbotDto);
		return ResponseEntity.status(200).body(chatbotDto);
	}
	
	@PutMapping("/")
	public ResponseEntity<?> editAll(@RequestBody ChatbotDto chatbotDto) {
		boolean result = chatbotDao.editAll(chatbotDto);
		if(result == false) {
			//return ResponseEntity.notFound().build();
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
	
	//수정
	@Operation(
		description = "챗봇 정보 변경",
		responses = {
			@ApiResponse(responseCode = "200",description = "변경 완료",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = ChatbotDto.class)
				)
			),
			@ApiResponse(responseCode = "404",description = "챗봇 정보 없음",
				content = @Content(
						mediaType = "text/plain",
						schema = @Schema(implementation = String.class), 
						examples = @ExampleObject("not found")
				)
			),
			@ApiResponse(responseCode = "500",description = "서버 오류",
				content = @Content(
						mediaType = "text/plain",
						schema = @Schema(implementation = String.class), 
						examples = @ExampleObject("server error")
				)
			),
		}
	)
	@PatchMapping("/")
	public ResponseEntity<?> edit(@RequestBody ChatbotDto chatbotDto) {
		boolean result = chatbotDao.edit(chatbotDto);
		if(result == false) {
			//return ResponseEntity.notFound().build();
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok().build();
	}
	
	//삭제
	@Operation(
		description = "챗봇 정보 삭제",
		responses = {
			@ApiResponse(responseCode = "200",description = "삭제 완료",
				content = @Content(
						mediaType = "text/plain",
						schema = @Schema(implementation = String.class),
						examples = @ExampleObject("ok")
				)
			),
			@ApiResponse(responseCode = "404",description = "챗봇 정보 없음",
				content = @Content(
						mediaType = "text/plain",
						schema = @Schema(implementation = String.class), 
						examples = @ExampleObject("not found")
				)
			),
			@ApiResponse(responseCode = "500",description = "서버 오류",
				content = @Content(
						mediaType = "text/plain",
						schema = @Schema(implementation = String.class), 
						examples = @ExampleObject("server error")
				)
			),
		}
	)
	@DeleteMapping("/{chatbotNo}")
	public ResponseEntity<?> delete(@PathVariable int chatbotNo) {
	    boolean result = chatbotDao.delete(chatbotNo);
	    if(result) {
	        return ResponseEntity.ok().build(); // 성공 상태 코드 반환
	    } else {
	        return ResponseEntity.notFound().build(); // 실패 상태 코드 반환
	    }
	}
}
