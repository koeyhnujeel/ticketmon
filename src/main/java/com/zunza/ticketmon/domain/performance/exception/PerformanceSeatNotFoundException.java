package com.zunza.ticketmon.domain.performance.exception;

import com.zunza.ticketmon.global.exception.CustomException;

import jakarta.servlet.http.HttpServletResponse;

public class PerformanceSeatNotFoundException extends CustomException {

	private static final String MESSAGE = "존재하지 않는 좌석입니다.";

	public PerformanceSeatNotFoundException() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return HttpServletResponse.SC_NOT_FOUND;
	}
}
