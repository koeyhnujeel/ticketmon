package com.zunza.ticketmon.global.common;

import lombok.Getter;

public enum RedisKeyPrefix {
	TEMPLOCKED("locked:seat:");

	@Getter
	private final String prefix;

	RedisKeyPrefix(String prefix) {
		this.prefix = prefix;
	}
}
