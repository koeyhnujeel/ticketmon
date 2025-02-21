package com.zunza.ticketmon.domain.performance.exception;

import com.zunza.ticketmon.global.exception.CustomException;

import jakarta.servlet.http.HttpServletResponse;

public class PerformanceNotFoundException extends CustomException {

	private static final String MESSAGE = "존재하지 않는 공연입니다.";

	public PerformanceNotFoundException() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return HttpServletResponse.SC_NOT_FOUND;
	}
}
