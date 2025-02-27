package com.zunza.ticketmon.domain.user;

import com.zunza.ticketmon.oauth2.dto.Oauth2Response;
import com.zunza.ticketmon.domain.user.dto.SignupRequestDto;
import com.zunza.ticketmon.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

	@Column(nullable = false)
	private String name;

	@Column(unique = true, nullable = false)
	private String email;

	@Column()
	private String password;

	@Column(unique = true)
	private String phone;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserType userType;

	@Builder
	private User(String name, String email, String password, String phone, UserType userType) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.userType = userType;
	}

	public static User createNormalUser(SignupRequestDto signupRequestDto) {
		return User.builder()
			.name(signupRequestDto.getName())
			.email(signupRequestDto.getEmail())
			.password(signupRequestDto.getPassword())
			.phone(signupRequestDto.getPhone())
			.userType(UserType.NORMAL)
			.build();
	}

	public static User createSocialUser(Oauth2Response oauth2Response) {
		return User.builder()
			.name(oauth2Response.getName())
			.email(oauth2Response.getEmail())
			.userType(UserType.SOCIAL)
			.build();
	}
}
