package com.zunza.ticketmon.global.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zunza.ticketmon.global.exception.TokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;

@Getter
@Component
public class JwtTokenProvider {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.access-token-validity}")
	private long accessTokenValidity;

	@Value("${jwt.refresh-token-validity}")
	private long refreshTokenValidity;

	private Key getKey() {
		byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateAccessToken(Long userId) {
		Claims claims = Jwts.claims();
		claims.put("userId", userId);

		Date now = new Date();
		Date validity = new Date(now.getTime() + accessTokenValidity);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(getKey(), SignatureAlgorithm.HS256)
			.compact();
	}

	public String generateRefreshToken() {
		Date now = new Date();
		Date validity = new Date(now.getTime() + refreshTokenValidity);

		return Jwts.builder()
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(getKey(), SignatureAlgorithm.HS256)
			.compact();
	}

	public boolean validateAccessToken(String accessToken) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(accessToken);
			return true;
		} catch (ExpiredJwtException e) {
			throw new TokenException("만료된 토큰입니다.");
		} catch (SignatureException e) {
			throw new TokenException("변조된 토큰입니다.");
		}
	}

	public boolean validateRefreshToken(String refreshToken) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(refreshToken);
			return true;
		} catch (ExpiredJwtException e) {
			throw new TokenException("만료된 리프레쉬 토큰입니다.");
		} catch (SignatureException e) {
			throw new TokenException("변조된 리프레쉬 토큰입니다.");
		}
	}

	public Authentication getAuthentication(String accessToken) {
		Claims claims = getClaims(accessToken);
		Long userId = claims.get("userId", Long.class);

		return new UsernamePasswordAuthenticationToken(
			userId,
			null,
			List.of(new SimpleGrantedAuthority("USER"))
		);
	}

	private Claims getClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(getKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public Map<String, Object> getPayloadFromExpiredToken(String expiredToken) {
		try {
			String[] parts = expiredToken.split("\\.");
			if (parts.length < 2) {
				throw new IllegalArgumentException("잘못된 JWT 형식입니다.");
			}

			String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);

			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(payload, Map.class);
		} catch (Exception e) {
			throw new RuntimeException("Payload 읽기 실패", e);
		}
	}
}
