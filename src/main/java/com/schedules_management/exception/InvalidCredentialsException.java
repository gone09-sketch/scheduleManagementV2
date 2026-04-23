
package com.schedules_management.exception;

import org.springframework.http.HttpStatus;

/** 이메일 또는 비밀번호 불일치 예외처리
 * 로그인 시, 이메일 혹은 비밀번호가 일치하지 않으면 이 예외를 던져준다.
 * 이메일과 비밀번호 중 어떤 것이 오류가 났는지 확실히 하지 않는다. => 두 개 모두 같은 예외 사용
 */

public class InvalidCredentialsException extends ServiceException {

    // 생성자
    public InvalidCredentialsException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);

    }

}
