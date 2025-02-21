package com.zunza.ticketmon.domain.ticket.exception;

import com.zunza.ticketmon.global.exception.CustomException;

import jakarta.servlet.http.HttpServletResponse;

public class SeatReservationInProgressException extends CustomException {

	private static final String MESSAGE = "예약 진행 중인 좌석입니다.";

	public SeatReservationInProgressException() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return HttpServletResponse.SC_CONFLICT;
	}
}
