package com.zunza.ticketmon.domain.user.exception;

import com.zunza.ticketmon.global.exception.CustomException;

import jakarta.servlet.http.HttpServletResponse;

public class UserNotFoundException extends CustomException {

	private static final String MESSAGE = "존재하지 않는 유저입니다.";

	public UserNotFoundException() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return HttpServletResponse.SC_NOT_FOUND;
	}
}
