package com.zunza.ticketmon.domain.ticket.exception;

import com.zunza.ticketmon.global.exception.CustomException;

import jakarta.servlet.http.HttpServletResponse;

public class SeatAlreadyReservedException extends CustomException {

	private static final String MESSAGE = "이미 예약된 좌석입니다.";

	public SeatAlreadyReservedException() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return HttpServletResponse.SC_CONFLICT;
	}
}
