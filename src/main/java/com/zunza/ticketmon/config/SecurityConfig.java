package com.zunza.ticketmon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zunza.ticketmon.global.security.JwtAuthenticationEntryPoint;
import com.zunza.ticketmon.global.security.JwtAuthenticationFilter;
import com.zunza.ticketmon.global.security.JwtExceptionFilter;
import com.zunza.ticketmon.global.security.JwtLoginFilter;
import com.zunza.ticketmon.global.security.JwtTokenProvider;
import com.zunza.ticketmon.global.security.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationConfiguration authenticationConfiguration;
	private final RefreshTokenRepository refreshTokenRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final ObjectMapper objectMapper;

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
			.requestMatchers("/error", "/favicon.ico");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/api/auth/test").authenticated()
				.anyRequest().permitAll()
			)

			.addFilterBefore(new JwtLoginFilter(
					authenticationConfiguration.getAuthenticationManager(),
					refreshTokenRepository,
					jwtTokenProvider,
					objectMapper),
				UsernamePasswordAuthenticationFilter.class)

			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
				UsernamePasswordAuthenticationFilter.class)

			.addFilterBefore(new JwtExceptionFilter(objectMapper),
				JwtAuthenticationFilter.class)

			.exceptionHandling(exceptionHandling -> exceptionHandling
				.authenticationEntryPoint(new JwtAuthenticationEntryPoint(objectMapper))
			);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
