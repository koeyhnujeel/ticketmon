package com.zunza.ticketmon.oauth2;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.zunza.ticketmon.global.security.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		CustomOauth2User oauth2User = (CustomOauth2User) authentication.getPrincipal();
		Long userId = oauth2User.getUserId();
		String username = oauth2User.getName();

		String encodeName = URLEncoder.encode(username, "UTF-8");

		String accessToken = jwtTokenProvider.generateAccessToken(userId);
		String redirectUrl = "http://localhost:5173/login?token=" + accessToken + "&username=" + encodeName;
		response.sendRedirect(redirectUrl);
	}
}
