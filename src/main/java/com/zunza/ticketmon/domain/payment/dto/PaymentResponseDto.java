package com.zunza.ticketmon.domain.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResponseDto {
	private BigDecimal totalPrice;
	private LocalDateTime transactionTime;
}
