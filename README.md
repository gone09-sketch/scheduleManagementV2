
# 일정 관리 앱

**프로젝트명:** 일정 관리 앱 

1. 일정을 생성, 수정, 조회, 삭제할 수 있습니다.
2. 로그인/로그아웃 기능을 구현하여 유저의 개인 일정을 관리할 수 있습니다.

<br> 

## 일정 API
*일정의 수정과 삭제는 로그인 후 가능합니다.
*유저 삭제(회원탈퇴)시, 해당 유저의 일정은 조회가 불가능합니다. 
- 일정 생성
- 일정 전체 조회
- 일정 단건 조회(유저 이름 필터)
- 일정 수정(일정 제몰, 일정 내용 수정 가능)
- 일정 삭제

## 유저 API
*유저의 정보 수정 및 회원탈퇴는 로그인 후 가능합니다. 
- 유저 생성(회원가입)
- 유저 전체 조회
- 유저 단건 조회(유저 이름 필터)
- 유저 정보 수정(이름을 제외한 이메일과 비밀번호만 수정 가능)
- 유저 삭제(회원탈퇴)

<br> 

## API 명세서 
```
노션URL: https://www.notion.so/CH4-API-344de79777f98026b1fecca4369aac23
```

<br> 

## ERD
<img width="1373" height="424" alt="image" src="https://github.com/user-attachments/assets/5ece7a55-b0ca-456b-8724-cf02d1820be4" />

<br>

## 프로젝트 구조

```bash
src/main/java/com/schedules_management/
├── users/
│   ├── controller/
│   ├── dto/
│   ├── service/
│   ├── entity/
│   └── repository/
├── schedules/
│   ├── controller/
│   ├── dto/
│   ├── service/
│   ├── entity/
│   └── repository/
├── session
│   └── SessionUser
├── exception
│   ├── handler/
│   ├── dto/
│   ├── DuplicateEmailException
│   ├── InvalidCredentialsException
│   ├── ScheduleNotFoundExcepiton
│   ├── ServiceException
│   ├── UnauthorizedException
│   └── UserNotFoundException
└── ScheduleDevelopApplication.java
```

---


