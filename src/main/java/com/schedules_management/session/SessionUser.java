package com.schedules_management.session;

/** 세션에 저장할 사용자 정보를 담는 DTO
 * 로그인 흐름:
 * 1. LoginRequest에서 이메일 + 패스워드 받기
 * 2. DB에서 이메일로 유저 조회(검증)
 * 3. 패스워드 일치 확인 (검증)
 * 4. SessionUser 객체 생성하여 (userID와 이메일) 세션 저장
 * -> 서버 메모리에 저장되기 때문에 패스워드는 SessionUser에 저장하지 않는다.
 * 5. 이후 요청은 세션은 userID로 사용자 식별
 */

public class SessionUser {
    // 속성
    private final Long userID;
    private final String email;


    // 생성자
    public SessionUser(Long userID, String email) {
        this.userID = userID;
        this.email = email;
    }

    // 기능
    // getter
    public Long getUserID() {
        return userID;
    }
    public String getEmail() {
        return email;
    }

}
