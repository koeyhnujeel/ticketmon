package com.zunza.ticketmon.domain.payment.exception;

import com.zunza.ticketmon.global.exception.CustomException;

import jakarta.servlet.http.HttpServletResponse;

public class CardInfoException extends CustomException {

	private static final String MESSAGE = "카드 정보가 올바르지 않습니다. 다시 확인해주세요.";

	public CardInfoException() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return HttpServletResponse.SC_BAD_REQUEST;
	}
}
