package com.schedules_management.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ServiceException {

    // 생성자
    public UserNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);

    }

}
