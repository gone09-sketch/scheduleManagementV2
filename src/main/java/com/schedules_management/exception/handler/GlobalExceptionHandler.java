package com.schedules_management.exception.handler;

import com.schedules_management.exception.ServiceException;
import com.schedules_management.exception.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** 예외가 오면 잡아서 응답으로 만들어주는 클래스
 * 저장할 데이터가 없다. 속성 및 생성자 필요 X
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponseDto> handlerServiceException(ServiceException e) {

        // 1. 바디로 들어갈 내용 (body)
        String message = e.getMessage();
        // 1-1. DTO로 변환
        ErrorResponseDto body = new ErrorResponseDto(message);

        // 2. 상태코드 준비 (header)
        HttpStatus status = e.getStatus();

        // 3. ResponseEntity 객체 생성
        /* HTTP 응답을 돌려줄 때 ResponseEntity 형식으로 줘야한다 -> 객체 생성
           1. HTTP 응답 -> header, body 로 나뉘어짐
           2. 헤더 -> 상태코드
           3. 바디 -> 메세지
         */
        ResponseEntity<ErrorResponseDto> exceptionResponse = new ResponseEntity<>(body, status);

        // 4. 반환
        return exceptionResponse;
    }

}
