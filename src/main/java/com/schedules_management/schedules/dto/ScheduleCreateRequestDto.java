package com.schedules_management.schedules.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ScheduleCreateRequestDto {
    // 속성
    @NotBlank(message = "일정 제목을 입력해주세요.")
    @Size(max = 25, message = "일정의 제목은 25글자 이내로 작성해주세요.")
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


