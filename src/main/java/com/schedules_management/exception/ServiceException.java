package com.schedules_management.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {
    // 속성
    private final HttpStatus status;

    // 생성자
    /**
     * @param status HTTP 상태코드
     * @param message  예외 메세지 (Service에 있는 메세지가 담김)
     */
    public ServiceException(HttpStatus status, String message) {
        super(message); // 부모 생성자 호출: 메세지는 RunTimeException에 저장
        this.status = status; // 상태는 여기에 저장
    }

    // 기능
    // getter
    public HttpStatus getStatus() {
        return status;
    }

}
