package com.zunza.ticketmon.global.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zunza.ticketmon.domain.user.dto.LoginRequestDto;
import com.zunza.ticketmon.global.common.ApiResponse;
import com.zunza.ticketmon.global.common.ErrorResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

	private final RefreshTokenRepository refreshTokenRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final ObjectMapper objectMapper;

	public JwtLoginFilter(AuthenticationManager authenticationManager, RefreshTokenRepository refreshTokenRepository,
		JwtTokenProvider jwtTokenProvider, ObjectMapper objectMapper) {
		this.authenticationManager = authenticationManager;
		this.refreshTokenRepository = refreshTokenRepository;
		this.jwtTokenProvider = jwtTokenProvider;
		this.objectMapper = objectMapper;
		setFilterProcessesUrl("/api/auth/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {

		LoginRequestDto loginRequestDto = getLoginRequestDto(request);
		String email = loginRequestDto.getEmail();
		String password = loginRequestDto.getPassword();

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
		return authenticationManager.authenticate(token);
	}

	private LoginRequestDto getLoginRequestDto(HttpServletRequest request) {
		try {
			return objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {

		CustomUserDetails details = (CustomUserDetails)authResult.getPrincipal();
		Long userId = details.getUserId();

		String accessToken = jwtTokenProvider.generateAccessToken(userId);
		String refreshToken = jwtTokenProvider.generateRefreshToken();
		refreshTokenRepository.saveRefreshToken(userId, refreshToken);

		TokenResponseDto tokenResponseDto = new TokenResponseDto(accessToken, refreshToken);
		ApiResponse<TokenResponseDto> apiResponse = ApiResponse.<TokenResponseDto>builder()
			.data(tokenResponseDto)
			.code(HttpServletResponse.SC_OK)
			.build();

		setResponse(response, HttpServletResponse.SC_OK);
		objectMapper.writeValue(response.getWriter(), apiResponse);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) throws IOException {

		ErrorResponse errorResponse = ErrorResponse.builder()
			.message("이메일 또는 비밀번호를 확인해 주세요.")
			.code(HttpServletResponse.SC_UNAUTHORIZED)
			.build();

		setResponse(response, HttpServletResponse.SC_UNAUTHORIZED);
		objectMapper.writeValue(response.getWriter(), errorResponse);
	}

	private void setResponse(HttpServletResponse response, int StatusCode) {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setStatus(StatusCode);
	}
}
