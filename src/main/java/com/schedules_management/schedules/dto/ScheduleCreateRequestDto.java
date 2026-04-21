package com.schedules_management.schedules.dto;

import jakarta.validation.constraints.NotBlank;

public class ScheduleCreateRequestDto {
    // 속성
    @NotBlank(message = "일정 제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "일정 내용을 입력해주세요.")
    private String content;

    // 생성자
    public ScheduleCreateRequestDto() {
    }


    // 기능
    // getter
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
}


