package com.zunza.ticketmon.global.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenRefreshResponseDto {
	private String newAccessToken;
	private String newRefreshToken;
}
