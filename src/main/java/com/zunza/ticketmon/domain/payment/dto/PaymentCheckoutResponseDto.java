package com.zunza.ticketmon.domain.payment.dto;

import java.math.BigDecimal;
import java.util.List;

import com.zunza.ticketmon.domain.ticket.dto.SelectedSeatDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentCheckoutResponseDto {
	private List<SelectedSeatDto> selectedSeatDto;
	private BigDecimal totalPrice;
}
