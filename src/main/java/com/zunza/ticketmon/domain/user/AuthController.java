package com.zunza.ticketmon.domain.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zunza.ticketmon.domain.user.dto.SignupRequestDto;
import com.zunza.ticketmon.domain.user.dto.TokenRequestDto;
import com.zunza.ticketmon.global.security.TokenResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
		authService.signup(signupRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/refresh")
	public ResponseEntity<TokenResponseDto> tokenRefresh(@RequestBody TokenRequestDto tokenRequestDto) {
		return ResponseEntity.ok(authService.refresh(tokenRequestDto));
	}

	@GetMapping("/test")
	public void test(@AuthenticationPrincipal Long userId) {
	}
}
