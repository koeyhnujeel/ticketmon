package com.zunza.ticketmon.global.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginSuccessResponseDto {
	private String username;
	private String accessToken;
	private String refreshToken;
}
