package com.zunza.ticketmon.global.exception;

import jakarta.servlet.http.HttpServletResponse;

public class TokenException extends CustomException{

	public TokenException(String message) {
		super(message);
	}

	@Override
	public int getStatusCode() {
		return HttpServletResponse.SC_UNAUTHORIZED;
	}
}
