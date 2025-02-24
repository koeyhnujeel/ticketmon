package com.zunza.ticketmon.domain.payment.exception;

import com.zunza.ticketmon.global.exception.CustomException;

import jakarta.servlet.http.HttpServletResponse;

public class CardBalanceException extends CustomException {

	private static final String MESSAGE = "잔액이 부족합니다. 다른 카드로 결제하거나 충전 후 다시 시도해주세요.";

	public CardBalanceException() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return HttpServletResponse.SC_PAYMENT_REQUIRED;
	}
}
