package com.zunza.ticketmon.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResponse<T> {

	private boolean success = true;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	private int code;

	@Builder
	public ApiResponse(T data, int code) {
		this.data = data;
		this.code = code;
	}
}
