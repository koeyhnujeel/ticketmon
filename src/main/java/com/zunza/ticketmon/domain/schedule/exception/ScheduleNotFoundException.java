package com.zunza.ticketmon.domain.schedule.exception;

import com.zunza.ticketmon.global.exception.CustomException;

import jakarta.servlet.http.HttpServletResponse;

public class ScheduleNotFoundException extends CustomException {

	private static final String MESSAGE = "해당 공연은 일정이 없습니다.";

	public ScheduleNotFoundException() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return HttpServletResponse.SC_NOT_FOUND;
	}
}
