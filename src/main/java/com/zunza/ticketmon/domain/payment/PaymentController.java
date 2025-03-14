package com.zunza.ticketmon.domain.payment;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.zunza.ticketmon.domain.payment.dto.PaymentCheckoutResponseDto;
import com.zunza.ticketmon.domain.payment.dto.PaymentRequestDto;
import com.zunza.ticketmon.domain.payment.dto.PaymentResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@GetMapping("/api/payments")
	public ResponseEntity<PaymentCheckoutResponseDto> getCheckoutInfo(
		@AuthenticationPrincipal Long userId
	) {
		return ResponseEntity.ok(paymentService.getCheckoutInfo(userId));
	}

	//TODO 만약 오류?로 인해 똑같은 요청이 두번 들어온다면? 생각해보기
	@PostMapping("/api/payments")
	public ResponseEntity<PaymentResponseDto> payment(
		@RequestHeader("X-User-Id") Long userId,
		@RequestBody PaymentRequestDto paymentRequestDto
	) {
		return ResponseEntity.ok(paymentService.payment(userId, paymentRequestDto));
	}
}
