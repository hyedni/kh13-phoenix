package com.kh.pheonix.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kh.pheonix.Vo.KakaoPayReadyRequestVO;
import com.kh.pheonix.Vo.KakaoPayReadyResponseVO;
import com.kh.pheonix.configuration.KakaoPayProperties;
import com.kh.pheonix.dao.PaymentDao;
import com.kh.pheonix.dao.ProductDao;

@Service //카카오페이 서비스를 진행할 남은 부분들... 
public class KakaoPayService {
	
	@Autowired
	private KakaoPayProperties kakaoPayProperties;
	@Autowired
	private RestTemplate template;
	@Autowired
	private HttpHeaders header;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private PaymentDao paymentDao;
	
	//준비요청 메소드(Ready)
	public KakaoPayReadyResponseVO ready(KakaoPayReadyRequestVO requestVO) throws URISyntaxException {
		//주소 생성
		URI uri = new URI("https://open-api.kakaopay.com/online/v1/payment/ready");
		
		//바디 생성
		//MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		Map<String, String> body = new HashMap<>();
		body.put("cid", kakaoPayProperties.getCid());
		body.put("partner_order_id", requestVO.getPartnerOrderId());
		body.put("partner_user_id", requestVO.getPartnerUserId());
		body.put("item_name", requestVO.getItemName());
		body.put("quantity", "1");
		body.put("total_amount", String.valueOf(requestVO.getTotalAmount()));
		body.put("tax_free_amount", "0");
		
		//구매페이지 주소의 뒤에 /success, /cancel, /fail을 붙여서 처리하도록 구현
		String page = ServletUriComponentsBuilder
								.fromCurrentRequestUri().build().toUriString();//현재페이지 구하기
		//현재페이지에 주소를 붙여 결제 결과에 따른 페이지를 각각 만들 수 있음.
		body.put("approval_url", page+"/success");
		body.put("cancel_url", page+"/cancel");
		body.put("fail_url", page+"/fail");
		
		//통신 요청
		HttpEntity entity = new HttpEntity(body, header);//헤더+바디
		
		return template.postForObject(uri, entity, KakaoPayReadyResponseVO.class);
	}
	
}
