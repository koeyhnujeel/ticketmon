package com.zunza.ticketmon.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenRequestDto {
	private String accessToken;
	private String refreshToken;
}
