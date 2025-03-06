package com.zunza.ticketmon.oauth2.dto;

import java.util.Map;

public class KakaoResponseDto implements Oauth2Response{

	private final Map<String, Object> attribute;
	private final Map<String, Object> kakaoAccount;

	public KakaoResponseDto(Map<String, Object> attribute) {
		this.attribute = attribute;
		this.kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
	}

	@Override
	public String getProvider() {
		return "kakao";
	}

	@Override
	public String getProviderId() {
		return attribute.get("id").toString();
	}

	@Override
	public String getEmail() {
		return kakaoAccount.get("email").toString();
	}

	@Override
	public String getName() {
		return kakaoAccount.get("name").toString();

	}
}
