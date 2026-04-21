package com.schedules_management.schedules.dto;

import java.time.LocalDateTime;

public class SchedulePatchResponseDto {
    // 속성
    private final Long scheduleID;
    private final String userName;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    // 생성자
    public SchedulePatchResponseDto(Long scheduleID, String userName,String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.scheduleID = scheduleID;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    // 기능
    // getter
    public Long getScheduleID() {
        return scheduleID;
    }
    public String getUserName() {
        return userName;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

}
