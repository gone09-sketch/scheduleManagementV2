package com.schedules_management.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ServiceException {

    // 생성자
    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);

    }

}
