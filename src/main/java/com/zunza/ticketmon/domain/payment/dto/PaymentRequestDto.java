package com.zunza.ticketmon.domain.payment.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentRequestDto {
	private List<Long> performanceSeatIds;
	private BigDecimal totalPrice;
}
