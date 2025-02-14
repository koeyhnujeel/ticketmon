package com.zunza.ticketmon.global.exception;

import jakarta.servlet.http.HttpServletResponse;

public class RefreshTokenNotFoundException extends CustomException {

	private static final String MESSAGE = "존재하지 않는 리프레쉬 토큰 입니다.";

	public RefreshTokenNotFoundException() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return HttpServletResponse.SC_NOT_FOUND;
	}
}
