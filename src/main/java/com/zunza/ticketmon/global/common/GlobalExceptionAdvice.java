package com.zunza.ticketmon.global.common;

import java.util.List;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zunza.ticketmon.global.exception.CustomException;

@RestControllerAdvice
public class GlobalExceptionAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {

		List<String> errorMessages = e.getFieldErrors().stream()
			.map(DefaultMessageSourceResolvable::getDefaultMessage)
			.toList();

		String message = errorMessages.size() == 1 ? errorMessages.get(0) : null;
		List<String> messages = errorMessages.size() > 1 ? errorMessages : null;
		int code = e.getStatusCode().value();

		ErrorResponse errorResponse = ErrorResponse.builder()
			.message(message)
			.messages(messages)
			.code(code)
			.build();

		return ResponseEntity.status(code).body(errorResponse);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> CustomExceptionHandler(CustomException e) {
		ErrorResponse errorResponse = ErrorResponse.builder()
			.message(e.getMessage())
			.code(e.getStatusCode())
			.build();

		return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
	}
}

