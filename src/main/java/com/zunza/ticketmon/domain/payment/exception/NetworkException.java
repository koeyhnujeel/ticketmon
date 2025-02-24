package com.zunza.ticketmon.domain.payment.exception;

import com.zunza.ticketmon.global.exception.CustomException;

public class NetworkException extends CustomException {

	private static final String MESSAGE = "결제 처리 중 네트워크 오류가 발생했습니다.";

	public NetworkException() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return 503;
	}
}
