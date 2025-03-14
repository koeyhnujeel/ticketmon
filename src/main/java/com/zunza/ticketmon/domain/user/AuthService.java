package com.zunza.ticketmon.domain.user;

import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zunza.ticketmon.domain.user.dto.SignupRequestDto;
import com.zunza.ticketmon.domain.user.dto.TokenRequestDto;
import com.zunza.ticketmon.domain.user.exception.DuplicateEmailException;
import com.zunza.ticketmon.global.exception.InvalidRefreshTokenException;
import com.zunza.ticketmon.global.security.JwtTokenProvider;
import com.zunza.ticketmon.global.security.RefreshTokenRepository;
import com.zunza.ticketmon.global.security.TokenRefreshResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final RefreshTokenRepository tokenRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public void signup(SignupRequestDto signupRequestDto) {
		if (userRepository.existsByEmail(signupRequestDto.getEmail())) {
			throw new DuplicateEmailException();
		}

		signupRequestDto.setEncodedPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
		userRepository.save(User.createNormalUser(signupRequestDto));
	}

	public TokenRefreshResponseDto refresh(TokenRequestDto tokenRequestDto) {
		Map<String, Object> payload = jwtTokenProvider.getPayloadFromExpiredToken(tokenRequestDto.getAccessToken());
		Integer userId = (Integer)payload.get("userId");

		String savedRt = tokenRepository.findByUserId(userId.longValue());
		String requestRt = tokenRequestDto.getRefreshToken();

		jwtTokenProvider.validateRefreshToken(requestRt);
		if (!requestRt.equals(savedRt)) {
			throw new InvalidRefreshTokenException();
		}

		String newAccessToken = jwtTokenProvider.generateAccessToken(userId.longValue());
		String newRefreshToken = jwtTokenProvider.generateRefreshToken();
		tokenRepository.saveRefreshToken(userId.longValue(), newRefreshToken);

		return new TokenRefreshResponseDto(newAccessToken, newRefreshToken);
	}
}
