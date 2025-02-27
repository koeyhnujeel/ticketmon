package com.zunza.ticketmon.oauth2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Oauth2UserDto {
	private Long userId;
	private String name;
	private String email;
}
