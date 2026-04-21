package com.schedules_management.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserCreateRequestDto {
    // 속성
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @Size(min = 8, message = "비밀번호는 8자 이상으로 입력해주세요.")
    private String password;


    // 생성자
    public UserCreateRequestDto() {}


    // 기능
    // getter
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

}
