package com.zunza.ticketmon.domain.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zunza.ticketmon.domain.user.dto.LoginRequestDto;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void init() {
		User user = User.builder()
			.name("홍길동")
			.email("test@email.com")
			.password(passwordEncoder.encode("password"))
			.phone("010-1234-5678")
			.build();
		userRepository.save(user);
	}

	@AfterEach
	public void destroy() {
		userRepository.deleteAll();
	}


	@Test
	@DisplayName("로그인 성공 - JWT 토큰 반환")
	void loginSuccess() throws Exception {
		// given
		LoginRequestDto loginRequest = new LoginRequestDto("test@email.com", "password");
		String jsonRequest = objectMapper.writeValueAsString(loginRequest);

		// when & then
		mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.accessToken").exists())
			.andExpect(jsonPath("$.data.refreshToken").exists());
	}

	@Test
	@DisplayName("로그인 실패 - 잘못된 비밀번호")
	void loginFail() throws Exception {
		// given
		LoginRequestDto loginRequest = new LoginRequestDto("test@email.com", "passworddd");
		String jsonRequest = objectMapper.writeValueAsString(loginRequest);

		// when & then
		mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)
			)
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("$.message").value("이메일 또는 비밀번호를 확인해 주세요."));
	}
}
