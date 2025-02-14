package com.zunza.ticketmon.global.security;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.zunza.ticketmon.global.exception.RefreshTokenNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RefreshTokenRepository {

	private final RedisTemplate<String, Object> redisTemplate;
	private final JwtTokenProvider jwtTokenProvider;

	private static final String KEY_PREFIX = "RT:";

	public void saveRefreshToken(Long userId, String refreshToken) {
		redisTemplate.opsForValue()
			.set(KEY_PREFIX + userId,
				refreshToken,
				jwtTokenProvider.getRefreshTokenValidity(),
				TimeUnit.MILLISECONDS
			);
	}

	public String findByUserId(Long userId) {
		Object refreshToken = redisTemplate.opsForValue().get(KEY_PREFIX + userId);
		if (refreshToken == null) {
			throw new RefreshTokenNotFoundException();
		}
		return refreshToken.toString();
	}
}
