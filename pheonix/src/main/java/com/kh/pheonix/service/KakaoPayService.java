package com.kh.pheonix.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kh.pheonix.Vo.KakaoPayApproveRequestVO;
import com.kh.pheonix.Vo.KakaoPayApproveResponseVO;
import com.kh.pheonix.Vo.KakaoPayCancelRequestVO;
import com.kh.pheonix.Vo.KakaoPayCancelResponseVO;
import com.kh.pheonix.Vo.KakaoPayOrderRequestVO;
import com.kh.pheonix.Vo.KakaoPayOrderResponseVO;
import com.kh.pheonix.Vo.KakaoPayReadyRequestVO;
import com.kh.pheonix.Vo.KakaoPayReadyResponseVO;
import com.kh.pheonix.Vo.PurchaseListVO;
import com.kh.pheonix.Vo.PurchaseVO;
import com.kh.pheonix.configuration.KakaoPayProperties;
import com.kh.pheonix.dao.PaymentDao;
import com.kh.pheonix.dao.ProductDao;
import com.kh.pheonix.dto.PaymentDetailDto;
import com.kh.pheonix.dto.PaymentDto;
import com.kh.pheonix.dto.ProductDto;

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
	
	//승인요청 메소드
	public KakaoPayApproveResponseVO approve(KakaoPayApproveRequestVO requestVO) throws URISyntaxException {
		//주소 생성
		URI uri = new URI("https://open-api.kakaopay.com/online/v1/payment/approve");
		
		//바디 생성
		//MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		Map<String, String> body = new HashMap<>();
		body.put("cid", kakaoPayProperties.getCid());
		body.put("tid", requestVO.getTid());
		body.put("pg_token", requestVO.getPgToken());
		body.put("partner_order_id", requestVO.getPartnerOrderId());
		body.put("partner_user_id", requestVO.getPartnerUserId());
		
		//통신 요청
		HttpEntity entity = new HttpEntity(body, header);//헤더+바디
		
		return template.postForObject(uri, entity, KakaoPayApproveResponseVO.class);
	}
	
	//여러 번의 등록 과정이 모두 성공하거나 모두 실패해야 한다
	// → 하나의 트랜잭션(transaction)으로 관리되어야 한다 (작업단위 설정)
	@Transactional
	public void insertPayment(PurchaseListVO vo,
						KakaoPayApproveResponseVO responseVO) {
		//DB에 결제 완료된 내역을 저장
		//- 결제 대표 정보(payment) = 번호생성 후 등록
		int paymentNo = paymentDao.paymentSequence();//번호생성
		PaymentDto paymentDto = PaymentDto.builder()
					.paymentNo(paymentNo)//시퀀스
					.paymentName(responseVO.getItemName())//대표결제명
					.paymentTotal(responseVO.getAmount().getTotal())//결제총금액
					.paymentRemain(responseVO.getAmount().getTotal())//잔여금액 - 결제총금액과 동일(첫 구매엔 취소가 없음.)
					.memberId(responseVO.getPartnerUserId())//구매자ID
					.paymentTid(responseVO.getTid())//거래번호
				.build();
		paymentDao.insertPayment(paymentDto);
		
		//- 결제 상세 내역(payment_detail) - 목록 개수만큼 반복적으로 등록
		for(PurchaseVO purchaseVO : vo.getPurchase()) {
			ProductDto productDto = productDao.selectOne(purchaseVO.getNo());//상품정보 조회
			
			int paymentDetailNo = paymentDao.paymentDetailSequence();
			PaymentDetailDto paymentDetailDto = PaymentDetailDto.builder()
						.paymentDetailNo(paymentDetailNo)//시퀀스
						.paymentDetailProduct(productDto.getProductNo())//상품번호
						.paymentDetailQty(purchaseVO.getQty())//수량
						.paymentDetailName(productDto.getProductName())//상품명
						.paymentDetailPrice(productDto.getProductPrice())//상품가격
						.paymentDetailStatus("승인")//결제상태
						.paymentNo(paymentNo)//결제대표번호
					.build();
			paymentDao.insertPaymentDetail(paymentDetailDto);//등록
		}
	}
	
	//주문조회(상세조회 메소드)
	public KakaoPayOrderResponseVO order(KakaoPayOrderRequestVO requestVO) throws URISyntaxException {
		URI uri = new URI("https://open-api.kakaopay.com/online/v1/payment/order");//조회요청주소
		
		Map<String, String> body = new HashMap<>();
		body.put("cid", kakaoPayProperties.getCid());
		body.put("tid", requestVO.getTid());
		
		HttpEntity entity = new HttpEntity(body, header);
		
		return template.postForObject(uri, entity, KakaoPayOrderResponseVO.class);
	}
	
	//취소 메소드
	public KakaoPayCancelResponseVO cancel(KakaoPayCancelRequestVO requestVO) throws URISyntaxException {
		URI uri = new URI("https://open-api.kakaopay.com/online/v1/payment/cancel");
		
		Map<String, String> body = new HashMap<>();
		body.put("cid", kakaoPayProperties.getCid());
		body.put("tid", requestVO.getTid());
		body.put("cancel_amount", String.valueOf(requestVO.getCancelAmount()));
		body.put("cancel_tax_free_amount", "0");
		
		HttpEntity entity = new HttpEntity(body, header);
		
		return template.postForObject(uri, entity, KakaoPayCancelResponseVO.class);
	}
}
