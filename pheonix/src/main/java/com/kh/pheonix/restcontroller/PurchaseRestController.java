package com.kh.pheonix.restcontroller;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pheonix.Vo.UserLoginVO;
import com.kh.pheonix.dao.CartDao;
import com.kh.pheonix.dao.PaymentDao;
import com.kh.pheonix.dao.ProductDao;
import com.kh.pheonix.dao.UserDao;
import com.kh.pheonix.dto.PaymentDetailDto;
import com.kh.pheonix.dto.PaymentDto;
import com.kh.pheonix.dto.ProductDto;
import com.kh.pheonix.kakaoPayVO.FlashInfoRequestVO;
import com.kh.pheonix.kakaoPayVO.FlashInfoVO;
import com.kh.pheonix.kakaoPayVO.FlashOneInfoRequestVO;
import com.kh.pheonix.kakaoPayVO.FlashOneInfoVO;
import com.kh.pheonix.kakaoPayVO.KakaoPayApproveRequestVO;
import com.kh.pheonix.kakaoPayVO.KakaoPayApproveResponseVO;
import com.kh.pheonix.kakaoPayVO.KakaoPayReadyRequestVO;
import com.kh.pheonix.kakaoPayVO.KakaoPayReadyResponseVO;
import com.kh.pheonix.kakaoPayVO.PurchaseAllPointVO;
import com.kh.pheonix.kakaoPayVO.PurchaseOnePointVO;
import com.kh.pheonix.kakaoPayVO.PurchasePointVO;
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
	private JwtService jwtService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PaymentDao paymentDao;
	
	@Autowired
	private CartDao cartDao;

///////////////구매(QR 화면 띄우기 및 정보 전달)
	@PostMapping("/")
	public FlashInfoVO purchase(@RequestBody PurchasePointVO purchasePointVO, @RequestHeader("Authorization") String refreshToken)
			throws URISyntaxException {
		UserLoginVO loginVO = jwtService.parse(refreshToken);

		List<PurchaseVO> list = purchasePointVO.getVo();
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
					) * purchaseVO.getQty() - purchasePointVO.getUsedPoint(); // total += 이 상품에 대한 구매 금액(가격*수량)
			
		}
		
		//사용포인트 차감
		userDao.usedPoint(loginVO.getUserId(), purchasePointVO.getUsedPoint());

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
	
	//단건 구매
	@PostMapping("/one")
	public FlashOneInfoVO purchase(@RequestBody PurchaseOnePointVO vo, @RequestHeader("Authorization") String refreshToken)
			throws URISyntaxException {
		UserLoginVO loginVO = jwtService.parse(refreshToken);

		PurchaseVO list = vo.getVo();
		
		//vo의 purchase 목록을 이용하여 결제 정보를 생성하는 코드
		StringBuffer itemName = new StringBuffer();
		int totalAmount = 0;

		ProductDto productDto = productDao.selectOne(list.getNo());// 상품정보 조회

		itemName.append(productDto.getProductName());// 이름(한 번만, i==0)
	
		totalAmount += (productDto.getProductPrice() 
				- Math.ceil( productDto.getProductPrice() * productDto.getProductDiscount() / 100) 
				) * list.getQty() - vo.getUsedPoint(); // total += 이 상품에 대한 구매 금액(가격*수량)
		
		//사용포인트 차감
		userDao.usedPoint(loginVO.getUserId(), vo.getUsedPoint());

		//결제 준비 요청 - KakaoPayReadyRequestVO, kakaoPayReadyResponseVO
		KakaoPayReadyRequestVO requestVO = KakaoPayReadyRequestVO.builder()
					.partnerOrderId(UUID.randomUUID().toString())
					.partnerUserId(loginVO.getUserId())
					.itemName(itemName.toString())
					.totalAmount(totalAmount)
				.build();
		KakaoPayReadyResponseVO responseVO = kakaoPayService.ready(requestVO);

		//flash Attribute 정보..
		FlashOneInfoVO flashOneInfoVO = new FlashOneInfoVO();
		flashOneInfoVO.setNextRedirectPcUrl(responseVO.getNextRedirectPcUrl());
		flashOneInfoVO.setPartnerOrderId(requestVO.getPartnerOrderId());
		flashOneInfoVO.setPartnerUserId(requestVO.getPartnerUserId());
		flashOneInfoVO.setTid(responseVO.getTid());
		flashOneInfoVO.setVo(list);

		return flashOneInfoVO;//원래 flash Attribute에 담는 정보를 프론트로 반환해줌.
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
	
	//단건 구매 승인
	@PostMapping("/success/one")
	public boolean successOne(@RequestBody FlashOneInfoRequestVO flashOneInfoRequestVO) throws URISyntaxException {
		
		KakaoPayApproveRequestVO requestVO = 
				KakaoPayApproveRequestVO.builder()
					.partnerOrderId(flashOneInfoRequestVO.getCartPartnerOrderId())
					.partnerUserId(flashOneInfoRequestVO.getCartPartnerUserId())
					.tid(flashOneInfoRequestVO.getCartTid())
					.pgToken(flashOneInfoRequestVO.getPgToken())
				.build();

		KakaoPayApproveResponseVO responseVO = kakaoPayService.approve(requestVO);
		
		//세션에 전송된 vo(구매목록)을 꺼내서 DB에 저장할 때 활용
		PurchaseVO list = flashOneInfoRequestVO.getPurchaseList();

		//DB에 결제 완료된 내역을 저장(모듈화)
		kakaoPayService.insertOnePayment(list, responseVO);
		
		//장바구니에 담긴 내역 삭제
		//flashInfoRequestVO.getCartPartnerUserId()랑 list.getNo()로 검색해서 삭제
//		for (PurchaseVO purchaseVO : list) {
//	        cartDao.delete(purchaseVO.getNo(), flashInfoRequestVO.getCartPartnerUserId());
//	    }
		
		
		return true;
	}
		
	//0원 결제(장바구니에서)
	@PostMapping("/zeroList")
	public void purchaseZeroList(@RequestBody PurchaseAllPointVO purchaseAllPointVO, @RequestHeader("Authorization") String refreshToken)
			throws URISyntaxException {
		UserLoginVO loginVO = jwtService.parse(refreshToken);

		List<PurchaseVO> list = purchaseAllPointVO.getVo();
		//vo의 purchase 목록을 이용하여 결제 정보를 생성하는 코드
		StringBuffer itemName = new StringBuffer();
		int totalAmount = 0;
		for (int i = 0; i < list.size(); i++) {
			PurchaseVO purchaseVO = list.get(i);
			ProductDto productDto = productDao.selectOne(purchaseVO.getNo());// 상품정보 조회
			if (i == 0) {
				itemName.append(productDto.getProductName());// 이름(한 번만, i==0)
			}
			totalAmount = 0; // total += 이 상품에 대한 구매 금액(가격*수량)
		}
		
		//사용포인트 차감
		userDao.usedPoint(loginVO.getUserId(), purchaseAllPointVO.getUsedPoint());

		//구매목록이 2개 이상이라면 "외 N건" 이라는 글자를 추가
		if (list.size() >= 2) {
			itemName.append(" 외 ");
			itemName.append(list.size() - 1);
			itemName.append("건");
		}
		
		int paymentNo = paymentDao.paymentSequence();//번호생성
		PaymentDto paymentDto = PaymentDto.builder()
				.paymentNo(paymentNo)//시퀀스
				.paymentName(itemName.toString())//대표결제명
				.paymentTotal(purchaseAllPointVO.getPaymentTotal())//결제총금액
				.paymentRemain(purchaseAllPointVO.getPaymentRemain())//잔여금액 - 결제총금액과 동일(첫 구매엔 취소가 없음.)
				.memberId(purchaseAllPointVO.getMemberId())//구매자ID
				.paymentTid(purchaseAllPointVO.getPaymentTid())//거래번호
			.build();
		paymentDao.insertPayment(paymentDto);
		
		//- 결제 상세 내역(payment_detail) - 목록 개수만큼 반복적으로 등록
		for(PurchaseVO purchaseVO : list) {
			
			ProductDto productDto = productDao.selectOne(purchaseVO.getNo());//상품정보 조회
			
			int paymentDetailNo = paymentDao.paymentDetailSequence();
			PaymentDetailDto paymentDetailDto = PaymentDetailDto.builder()
						.paymentDetailNo(paymentDetailNo)//시퀀스
						.paymentDetailProduct(productDto.getProductNo())//상품번호
						.paymentDetailQty(paymentDto.getQty())//수량
						.paymentDetailName(productDto.getProductName())//상품명
						.paymentDetailPrice(productDto.getProductPrice())//상품가격
						.paymentDetailStatus("승인")//결제상태
						.paymentNo(paymentDto.getPaymentNo())//결제대표번호
					.build();
			paymentDao.insertPaymentDetail(paymentDetailDto);//등록
		
		}

		
		//장바구니 비우기 및 포인트인 경우 충전
		Pattern pattern = Pattern.compile("\\d+");
		for (PurchaseVO purchaseVO : list) {
			ProductDto productDto = productDao.selectOne(purchaseVO.getNo());//상품정보 조회
			if(productDto.getProductType().equals("포인트")) {
				Matcher matcher = pattern.matcher(productDto.getProductContent());
				int number = 0;
				while (matcher.find()) {
					String numberStr = matcher.group(); // 매칭된 숫자 문자열
		            number = Integer.parseInt(numberStr); // 문자열을 정수로 변환
		        }
				userDao.editPoint(number, purchaseAllPointVO.getMemberId());
			}
	        cartDao.delete(purchaseVO.getNo(), purchaseAllPointVO.getMemberId());
	    }
		
	}
	
	//0원 결제(단건)
	@PostMapping("/zero")
	public void purchaseZero(@RequestBody PaymentDto paymentDto, @RequestHeader("Authorization") String refreshToken)
			throws URISyntaxException {
		System.out.println(paymentDto);
		UserLoginVO loginVO = jwtService.parse(refreshToken);

		ProductDto productDto = productDao.selectOne(paymentDto.getNo());// 상품정보 조회
		paymentDto.setPaymentName(productDto.getProductName());
		
		//사용포인트 차감
		userDao.usedPoint(loginVO.getUserId(), paymentDto.getUsedPoint());
		
		int paymentNo = paymentDao.paymentSequence();//번호생성
		//paymentDto.setPaymentNo(paymentNo);
		paymentDto = PaymentDto.builder()
				.paymentNo(paymentNo)//시퀀스
				.paymentName(paymentDto.getPaymentName())//대표결제명
				.paymentTotal(paymentDto.getPaymentTotal())//결제총금액
				.paymentRemain(paymentDto.getPaymentRemain())//잔여금액 - 결제총금액과 동일(첫 구매엔 취소가 없음.)
				.memberId(paymentDto.getMemberId())//구매자ID
				.paymentTid(paymentDto.getPaymentTid())//거래번호
			.build();
		paymentDao.insertPayment(paymentDto);
		
		//- 결제 상세 내역(payment_detail) - 목록 개수만큼 반복적으로 등록
//		ProductDto productDto = productDao.selectOne(paymentDto.getNo());//상품정보 조회
		
		int paymentDetailNo = paymentDao.paymentDetailSequence();
		PaymentDetailDto paymentDetailDto = PaymentDetailDto.builder()
					.paymentDetailNo(paymentDetailNo)//시퀀스
					.paymentDetailProduct(productDto.getProductNo())//상품번호
					.paymentDetailQty(paymentDto.getQty())//수량
					.paymentDetailName(productDto.getProductName())//상품명
					.paymentDetailPrice(productDto.getProductPrice())//상품가격
					.paymentDetailStatus("승인")//결제상태
					.paymentNo(paymentDto.getPaymentNo())//결제대표번호
				.build();
		paymentDao.insertPaymentDetail(paymentDetailDto);//등록
	
		//장바구니 비우기 및 포인트인 경우 충전
		Pattern pattern = Pattern.compile("\\d+");
		if(productDto.getProductType().equals("포인트")) {
			Matcher matcher = pattern.matcher(productDto.getProductContent());
			int number = 0;
			while (matcher.find()) {
				String numberStr = matcher.group(); // 매칭된 숫자 문자열
	            number = Integer.parseInt(numberStr); // 문자열을 정수로 변환
	        }
			userDao.editPoint(number, paymentDto.getMemberId());
		}
//		kakaoPayService.insertZero(paymentDto);
	}
	
	
	//마이페이지
	//구매 내역 조회
	@GetMapping("/myPurchaseList")
	public List<PaymentDto> myPurchaseList(@RequestHeader("Authorization") String refreshToken) {
		UserLoginVO loginVO = jwtService.parse(refreshToken);
		return paymentDao.paymentList(loginVO.getUserId());
	}
	//구매 내역 상세
	@GetMapping("/myPurchaseDetailList/{paymentNo}")
	public List<PaymentDetailDto> myPurchaseDetailList(@PathVariable int paymentNo, @RequestHeader("Authorization") String refreshToken) {
		UserLoginVO loginVO = jwtService.parse(refreshToken);
		return paymentDao.paymentDetailList(paymentNo);
	}


}
