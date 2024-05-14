package com.kh.pheonix.restcontroller;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.Vo.UserLoginVO;
import com.kh.pheonix.dao.CartDao;
import com.kh.pheonix.dao.PaymentDao;
import com.kh.pheonix.dao.ProductDao;
import com.kh.pheonix.dto.ProductDto;
import com.kh.pheonix.kakaoPayVO.FlashInfoRequestVO;
import com.kh.pheonix.kakaoPayVO.FlashInfoVO;
import com.kh.pheonix.kakaoPayVO.KakaoPayApproveRequestVO;
import com.kh.pheonix.kakaoPayVO.KakaoPayApproveResponseVO;
import com.kh.pheonix.kakaoPayVO.KakaoPayReadyRequestVO;
import com.kh.pheonix.kakaoPayVO.KakaoPayReadyResponseVO;
import com.kh.pheonix.kakaoPayVO.PurchaseVO;
import com.kh.pheonix.service.JwtService;
import com.kh.pheonix.service.KakaoPayService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/purchase")
public class PurchaseRestController {
	
	@Autowired
	private KakaoPayService kakaoPayService;

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private PaymentDao paymentDao;

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private CartDao cartDao;
	

///////////////구매(QR 화면 띄우기 및 정보 전달)
	@PostMapping("/")
	public FlashInfoVO purchase(@RequestBody List<PurchaseVO> list, @RequestHeader("Authorization") String refreshToken)
			throws URISyntaxException {
		UserLoginVO loginVO = jwtService.parse(refreshToken);

		log.debug("size = {}", list.size());
		log.debug("list = {}", list);
		//vo의 purchase 목록을 이용하여 결제 정보를 생성하는 코드
		StringBuffer itemName = new StringBuffer();
		int totalAmount = 0;
		for (int i = 0; i < list.size(); i++) {
			PurchaseVO purchaseVO = list.get(i);
			ProductDto productDto = productDao.selectOne(purchaseVO.getNo());// 상품정보 조회
			if (i == 0) {
				itemName.append(productDto.getProductName());// 이름(한 번만, i==0)
			}
			totalAmount += (productDto.getProductPrice() 
					- Math.ceil( productDto.getProductPrice() * productDto.getProductDiscount() / 100) 
					) * purchaseVO.getQty(); // total += 이 상품에 대한 구매 금액(가격*수량)
			
		}

		//구매목록이 2개 이상이라면 "외 N건" 이라는 글자를 추가
		if (list.size() >= 2) {
			itemName.append(" 외 ");
			itemName.append(list.size() - 1);
			itemName.append("건");
		}

		log.debug("결제 이름 = {}", itemName);
		log.debug("결제금액 = {}", totalAmount);

		//결제 준비 요청 - KakaoPayReadyRequestVO, kakaoPayReadyResponseVO
		KakaoPayReadyRequestVO requestVO = KakaoPayReadyRequestVO.builder()
					.partnerOrderId(UUID.randomUUID().toString())
					.partnerUserId(loginVO.getUserId())
					.itemName(itemName.toString())
					.totalAmount(totalAmount)
				.build();
		KakaoPayReadyResponseVO responseVO = kakaoPayService.ready(requestVO);

		//flash Attribute 정보..
		FlashInfoVO flashInfoVO = new FlashInfoVO();
		flashInfoVO.setNextRedirectPcUrl(responseVO.getNextRedirectPcUrl());
		flashInfoVO.setPartnerOrderId(requestVO.getPartnerOrderId());
		flashInfoVO.setPartnerUserId(requestVO.getPartnerUserId());
		flashInfoVO.setTid(responseVO.getTid());
		flashInfoVO.setVo(list);

		return flashInfoVO;//원래 flash Attribute에 담는 정보를 프론트로 반환해줌.
	}

		
	@PostMapping("/success")
	public boolean success(@RequestBody FlashInfoRequestVO flashInfoRequestVO) throws URISyntaxException {
		
		KakaoPayApproveRequestVO requestVO = 
				KakaoPayApproveRequestVO.builder()
					.partnerOrderId(flashInfoRequestVO.getCartPartnerOrderId())
					.partnerUserId(flashInfoRequestVO.getCartPartnerUserId())
					.tid(flashInfoRequestVO.getCartTid())
					.pgToken(flashInfoRequestVO.getPgToken())
				.build();

		KakaoPayApproveResponseVO responseVO = kakaoPayService.approve(requestVO);
		
		//세션에 전송된 vo(구매목록)을 꺼내서 DB에 저장할 때 활용
		List<PurchaseVO> list = flashInfoRequestVO.getPurchaseList();

		//DB에 결제 완료된 내역을 저장(모듈화)
		kakaoPayService.insertPayment(list, responseVO);
		
		//장바구니에 담긴 내역 삭제
		//flashInfoRequestVO.getCartPartnerUserId()랑 list.getNo()로 검색해서 삭제
//		for (PurchaseVO purchaseVO : list) {
//	        cartDao.delete(purchaseVO.getNo(), flashInfoRequestVO.getCartPartnerUserId());
//	    }
		
		
		return true;
	}
}
