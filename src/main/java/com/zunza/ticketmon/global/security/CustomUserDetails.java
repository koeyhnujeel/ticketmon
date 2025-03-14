package com.zunza.ticketmon.global.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.zunza.ticketmon.domain.user.User;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {

	private final Long userId;
	private final String username;
	private final String password;
	private final List<GrantedAuthority> authorities;

	public CustomUserDetails(User user) {
		this.userId = user.getId();
		this.username = user.getName();
		this.password = user.getPassword();
		this.authorities = List.of(new SimpleGrantedAuthority("USER"));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
