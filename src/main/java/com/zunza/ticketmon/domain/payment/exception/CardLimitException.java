package com.zunza.ticketmon.domain.payment.exception;

import com.zunza.ticketmon.global.exception.CustomException;

import jakarta.servlet.http.HttpServletResponse;

public class CardLimitException extends CustomException {

	private static final String MESSAGE = "카드 한도를 초과하였습니다. 다른 카드로 결제해주세요.";

	public CardLimitException() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return HttpServletResponse.SC_PAYMENT_REQUIRED;
	}
}
