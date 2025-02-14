package com.zunza.ticketmon.domain.user.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {

	@NotBlank(message = "이름을 입력해 주세요.")
	private String name;

	@Email(message = "유효한 이메일 형식이 아닙니다.")
	@NotBlank(message = "이메일을 입력해 주세요.")
	private String email;

	@NotBlank(message = "비밀번호를 입력해 주세요.")
	@Length(min = 8, max = 16, message = "비밀번호는 8자~16자로 설정해 주세요.")
	private String password;

	@NotBlank(message = "핸드폰 번호를 입력해 주세요.")
	private String phone;

	public void setEncodedPassword(String encodedPassword) {
		this.password = encodedPassword;
	}
}
