package com.zunza.ticketmon.global.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {

	private boolean success = false;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> messages;

	private int code;

	@Builder
	public ErrorResponse(String message, List<String> messages, int code) {
		this.message = message;
		this.messages = messages;
		this.code = code;
	}
}
