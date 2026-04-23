package com.schedules_management.exception;

import org.springframework.http.HttpStatus;

public class ScheduleNotFoundException extends ServiceException {

    // 생성자
    public ScheduleNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);

    }


}
