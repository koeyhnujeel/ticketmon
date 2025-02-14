package com.zunza.ticketmon.domain.user;

import com.zunza.ticketmon.domain.user.dto.SignupRequestDto;
import com.zunza.ticketmon.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

	@Column(nullable = false)
	private String password;

	@Column(unique = true, nullable = false)
	private String phone;

	@Builder
	private User(String name, String email, String password, String phone) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
	}

	public static User from(SignupRequestDto signupRequestDto) {
		return User.builder()
			.name(signupRequestDto.getName())
			.email(signupRequestDto.getEmail())
			.password(signupRequestDto.getPassword())
			.phone(signupRequestDto.getPhone())
			.build();
	}
}
