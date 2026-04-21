package com.schedules_management.users.dto;

import java.time.LocalDateTime;

public class UserGetResponseDto {
    // 속성
    private final Long userID;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;


    // 생성자
    public UserGetResponseDto(Long userID, String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    // 기능
    // getter
    public Long getUserID() {
        return userID;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
