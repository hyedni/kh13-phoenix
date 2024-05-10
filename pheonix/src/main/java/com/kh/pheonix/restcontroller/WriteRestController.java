package com.kh.pheonix.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.Vo.PersonalWriteVO;
import com.kh.pheonix.dao.PersonalDao;
import com.kh.pheonix.dao.WriteDao;
import com.kh.pheonix.dto.PersonalDto;
import com.kh.pheonix.dto.WriteDto;

@CrossOrigin
@RestController
@RequestMapping("/write")
public class WriteRestController {

	@Autowired
	private WriteDao writeDao;
	
	@Autowired
	private PersonalDao personalDao;
	
	@GetMapping("/")//조회
	public List<WriteDto> list() {
		return writeDao.selectList();
	}
	
	@PostMapping("/")//등록
	public void insert(@RequestBody WriteDto writeDto) {
		int sequence = writeDao.sequence();
		writeDto.setWriteNo(sequence);
		System.out.println(writeDto);
		writeDao.insert(writeDto);
	}
	
	@PutMapping("/")//전체수정
	public boolean edit(@RequestBody WriteDto writeDto) {
		boolean result = writeDao.update(writeDto);
		return result;
	}
	
	@DeleteMapping("/{writeNo}")//삭제
	public boolean delete(@PathVariable int writeNo) {
		return writeDao.delete(writeNo);
	}
	
	@GetMapping("/{writeNo}")//상세조회
	public WriteDto find(@PathVariable int writeNo) {
		WriteDto writeDto = writeDao.selectOne(writeNo);
		return writeDto;
	}
	
	@PostMapping("/reply") // 답글 등록
	public void insertReply(@RequestBody WriteDto writeDto) {
	    writeDao.insertReply(writeDto);
	}
	
	//1:1문의 + 답글 조회
	@GetMapping("/combine/{personalId}")
	public List<PersonalWriteVO> personalList(@PathVariable String personalId) {
	    List<PersonalDto> list = personalDao.selectList();
	    // PersonalDto를 PersonalWriteVO로 변환하는 코드가 필요합니다.
	    List<PersonalWriteVO> resultList = convertToPersonalWriteVO(list);
	    return resultList;
	}
	
	private List<PersonalWriteVO> convertToPersonalWriteVO(List<PersonalDto> dtoList) {
	    List<PersonalWriteVO> result = new ArrayList<>();
	    // PersonalDto를 PersonalWriteVO로 변환하는 로직을 구현합니다.
	    for (PersonalDto dto : dtoList) {
	        PersonalWriteVO vo = new PersonalWriteVO();
	        // PersonalDto의 필드 값을 PersonalWriteVO의 필드로 복사하거나 변환하는 등의 작업을 수행합니다.
	        result.add(vo);
	    }
	    return result;
	}
	}

