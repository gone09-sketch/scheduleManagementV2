package com.schedules_management.schedules.dto;

import java.time.LocalDateTime;

public class ScheduleGetResponseDto {
    // 속성
    private final Long scheduleID;
    private final String title;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    // 생성자
    public ScheduleGetResponseDto(Long scheduleID, String title, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.scheduleID = scheduleID;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    // 기능
    // getter
    public Long getScheduleID() {
        return scheduleID;
    }
    public String getTitle() {
        return title;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

}
