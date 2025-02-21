package com.zunza.ticketmon.domain.ticket.exception;

import com.zunza.ticketmon.global.exception.CustomException;

import jakarta.servlet.http.HttpServletResponse;

public class InvalidUserInfoException extends CustomException {

    private static final String MESSAGE = "사용자 정보가 일치하지 않습니다.";

    public InvalidUserInfoException() {
      super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
      return HttpServletResponse.SC_FORBIDDEN;
    }
}
