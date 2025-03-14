package com.zunza.ticketmon.oauth2;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.zunza.ticketmon.domain.user.User;
import com.zunza.ticketmon.domain.user.UserRepository;
import com.zunza.ticketmon.domain.user.UserType;
import com.zunza.ticketmon.oauth2.dto.GoogleResponseDto;
import com.zunza.ticketmon.oauth2.dto.KakaoResponseDto;
import com.zunza.ticketmon.oauth2.dto.NaverResponseDto;
import com.zunza.ticketmon.oauth2.dto.Oauth2Response;
import com.zunza.ticketmon.oauth2.dto.Oauth2UserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User = super.loadUser(userRequest);
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		Oauth2Response oauth2Response = getOauth2Response(oauth2User, registrationId);

		return userRepository.findByEmail(oauth2Response.getEmail())
			.map(user -> {
				if (user.getUserType() != UserType.SOCIAL) {
					throw new IllegalArgumentException();
				}

				return createCustomOauth2User(user, oauth2Response);
			})
			.orElseGet(() -> {
				User socialUser = User.createSocialUser(oauth2Response);
				User saved = userRepository.save(socialUser);
				return createCustomOauth2User(saved, oauth2Response);
			});
	}

	private Oauth2Response getOauth2Response(OAuth2User oAuth2User, String registrationId) {
		return switch (registrationId) {
			case "google" -> new GoogleResponseDto(oAuth2User.getAttributes());
			case "naver" -> new NaverResponseDto(oAuth2User.getAttributes());
			case "kakao" -> new KakaoResponseDto(oAuth2User.getAttributes());
			default -> throw new IllegalArgumentException("지원하지 않는 Provider 입니다.");
		};
	}

	private CustomOauth2User createCustomOauth2User(User user, Oauth2Response oauth2Response) {
		Oauth2UserDto oauth2UserDto = new Oauth2UserDto(user.getId(), oauth2Response.getName(), oauth2Response.getEmail());
		return new CustomOauth2User(oauth2UserDto);
	}
}
