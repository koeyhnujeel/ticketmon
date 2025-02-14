package com.zunza.ticketmon.global.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.zunza.ticketmon.domain.user.User;
import com.zunza.ticketmon.domain.user.UserRepository;
import com.zunza.ticketmon.domain.user.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(UserNotFoundException::new);
		return new CustomUserDetails(user);
	}
}
