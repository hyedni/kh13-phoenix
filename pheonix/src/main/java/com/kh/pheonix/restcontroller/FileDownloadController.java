package com.kh.pheonix.restcontroller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.pheonix.dao.AttachDao;
import com.kh.pheonix.dto.AttachDto;
import com.kh.pheonix.service.AttachProperties;

@CrossOrigin
@Controller
@RequestMapping("/download")
// 사용자에게 서버의 파일을 전송하는 것
// 저장된 파일들에 주소를 부여해줘야 한다
// 특정 주소로 들어가면 서버에서 자바 코드로 파일을 읽어와서 전송
// apache commons io와 apache commons fileupload 사용 (pom.xml에 추가)
// 파일을 내보내기 위한 전용 컨트롤러를 만들어서 처리
public class FileDownloadController {
	
	@Autowired
	private AttachDao attachDao;
	
	@Autowired
	private AttachProperties attachProperties;
	
	// 여태까지 모든 controller에서는 사용자가 볼 화면을 반환했다 (String)
	// 이 매핑은 그렇지않다는걸 표시 (@ResponseBody) : 화면을 주지 않을거다
	// 사용자에게 화면이 아닌 파일이담긴 응답객체를 반환
	// jsp화면을 반환하는게 아니라서 모든 작업을 수작업으로..
	// - ByteArrayResource : 파일의 내용 | ResponseEntity : 사용자에게 보내질 응답객체
	// 파일테이블의 pk를 전달받아 해당파일을 사용자에게 반환 
	@RequestMapping
	@ResponseBody
	public ResponseEntity<Resource> download(@RequestParam int attachNo) throws IOException {
		
		// [1] attachNo로 파일 정보 불러오기 (AttachDto)
		AttachDto attachDto = attachDao.find(attachNo);
		
		// [2] attachDto가 없으면 404 처리 
		if (attachDto == null) {
			return ResponseEntity.notFound().build();
		}
		
		// [3] 실제 파일을 불러온다 (apache commons io, apache commons fileupload)
		File dir = new File (attachProperties.getPath());
		File target = new File (dir, String.valueOf(attachDto.getAttachNo()));

		byte[] data = FileUtils.readFileToByteArray(target); 
		//파일을 읽어라 : apache commons 라이브러리가 있기 때문에 사용 가능 
		ByteArrayResource resource = new ByteArrayResource(data); 
		//포장 (데이터 래핑)
		
		if (!target.exists()) {
	        return ResponseEntity.notFound().build();
	    }
		
		String mimeType = Files.probeContentType(target.toPath());
	    MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM; // Default type if unknown
		
	    if (mimeType != null && mimeType.equals("image/jpeg")) {
	        mediaType = MediaType.IMAGE_JPEG;
	    } else if (mimeType != null && mimeType.equals("image/png")) {
	        mediaType = MediaType.IMAGE_PNG;
	    }

	    Resource fileAsResource = new UrlResource(target.toURI());

	    return ResponseEntity.ok()
	        .contentType(mediaType)
	        .header(HttpHeaders.CONTENT_DISPOSITION, attachDto.getAttachName())
	        .body(fileAsResource);
	   
	}
}

