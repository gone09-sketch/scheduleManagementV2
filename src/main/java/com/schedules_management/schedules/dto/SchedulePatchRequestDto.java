package com.schedules_management.schedules.dto;

import jakarta.validation.constraints.NotBlank;

public class SchedulePatchRequestDto {
    // 속성
    private String title;
    private String content;

    // 생성자
    public SchedulePatchRequestDto() {}


    // 기능
    // getter
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }

}
