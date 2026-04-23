package com.schedules_management.exception.dto;

public class ErrorResponseDto {

    // 속성
    private final String message;

    // 생성자
    public ErrorResponseDto(String message) {
        this.message = message;
    }

    // 기능
    // getter
    public String getMessage() {
        return message;
    }
}
