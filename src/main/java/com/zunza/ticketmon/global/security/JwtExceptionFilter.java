package com.zunza.ticketmon.global.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zunza.ticketmon.global.common.ErrorResponse;
import com.zunza.ticketmon.global.exception.TokenException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try {
			filterChain.doFilter(request, response);
		} catch (TokenException e) {
			ErrorResponse errorResponse = ErrorResponse.builder()
				.message(e.getMessage())
				.code(e.getStatusCode())
				.build();

			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			objectMapper.writeValue(response.getWriter(), errorResponse);
		}
	}
}
