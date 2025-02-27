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
		log.info(oauth2User.toString());

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		Oauth2Response oauth2Response;

		switch (registrationId) {
			case "google" -> oauth2Response = new GoogleResponseDto(oauth2User.getAttributes());
			default -> { return null; }
		}

		User user = userRepository.findByEmailForOauth2(oauth2Response.getEmail());

		if (user != null) {
			if (user.getUserType() == UserType.SOCIAL) {
				Oauth2UserDto oauth2UserDto = new Oauth2UserDto(user.getId(), oauth2Response.getName(),
					oauth2Response.getEmail());
				return new CustomOauth2User(oauth2UserDto);
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			User socialUser = User.createSocialUser(oauth2Response);
			User saved = userRepository.save(socialUser);
			Oauth2UserDto oauth2UserDto = new Oauth2UserDto(saved.getId(), oauth2Response.getName(), oauth2Response.getEmail());
			return new CustomOauth2User(oauth2UserDto);
		}
	}
}
