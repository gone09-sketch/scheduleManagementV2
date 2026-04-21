package com.schedules_management.users.dto;

public class UserPatchRequestDto {
    // 속성
    private String email;
    private String password;

    // 생성자
    protected UserPatchRequestDto() {}


    // 기능
    // getter
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

}
