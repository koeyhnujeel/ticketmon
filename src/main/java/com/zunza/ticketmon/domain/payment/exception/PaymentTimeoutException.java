package com.zunza.ticketmon.domain.payment.exception;

import com.zunza.ticketmon.global.exception.CustomException;

import jakarta.servlet.http.HttpServletResponse;

public class PaymentTimeoutException extends CustomException {

	private static final String MESSAGE = "결제 시간이 초과되었습니다. 다시 시도해 주시기 바랍니다.";

	public PaymentTimeoutException() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return HttpServletResponse.SC_REQUEST_TIMEOUT;
	}
}
