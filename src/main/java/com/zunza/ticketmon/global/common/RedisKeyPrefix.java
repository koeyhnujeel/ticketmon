package com.zunza.ticketmon.global.common;

import lombok.Getter;

public enum RedisKeyPrefix {
	TEMP_LOCKED("locked:seat:"),
	USER_SELECTED_SEAT("user:selected:seat:");

	@Getter
	private final String prefix;

	RedisKeyPrefix(String prefix) {
		this.prefix = prefix;
	}
}
