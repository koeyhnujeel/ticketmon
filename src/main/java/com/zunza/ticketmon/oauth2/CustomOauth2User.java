package com.zunza.ticketmon.oauth2;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.zunza.ticketmon.oauth2.dto.Oauth2UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomOauth2User implements OAuth2User {

	private final Oauth2UserDto oauth2UserDto;

	@Override
	public Map<String, Object> getAttributes() {
		return Map.of();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("USER"));
	}

	@Override
	public String getName() {
		return oauth2UserDto.getName();
	}

	public String getEmail() {
		return oauth2UserDto.getEmail();
	}

	public Long getUserId() {
		return oauth2UserDto.getUserId();
	}
}
