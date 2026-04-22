package com.schedules_management.users.controller;

import com.schedules_management.session.LoginRequestDto;
import com.schedules_management.session.SessionUser;
import com.schedules_management.users.dto.*;
import com.schedules_management.users.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")

public class UserController {
    // 속성
    private UserService userService;

    // 생성자
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 기능
    // 유저생성(회원가입)
    @PostMapping
    public ResponseEntity<UserCreateResponseDto> createUserAPI(
            @Valid
            @RequestBody UserCreateRequestDto createRequestDto) {

        UserCreateResponseDto createResponseAPI = userService.createUser(createRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponseAPI);
    }

    // 유저 전체조회 (이름 조회 가능)
    @GetMapping
    public ResponseEntity<List<UserGetResponseDto>> getAllUserAPI(
            @RequestParam(required = false) String userName) {
        List<UserGetResponseDto> getAllResponseAPI = userService.getAllUser(userName);
        return ResponseEntity.status(HttpStatus.OK).body(getAllResponseAPI);

    }

    // 유저 단건조회
    @GetMapping("/{userID}")
    public ResponseEntity<UserGetResponseDto> getOneUserAPI(
            @PathVariable("userID") Long userID) {

        UserGetResponseDto getOneResponseAPI = userService.getOneUser(userID);
        return ResponseEntity.status(HttpStatus.OK).body(getOneResponseAPI);
    }

    // 유저수정
    @PatchMapping
    public ResponseEntity<UserPatchResponseDto> patchUserAPI(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @RequestBody UserPatchRequestDto patchRequestDto) {

        // 로그인이 되었는지 확인
        if(sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserPatchResponseDto patchResponseAPI = userService.patchUser(sessionUser.getUserID(), patchRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(patchResponseAPI);
    }

    // 유저삭제
    @DeleteMapping("/{userID}")
    public ResponseEntity<Void> deleteUserAPI(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            HttpSession httpSession) {

        // 로그인이 되었는지 확인
        if(sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 유저 삭제
        userService.deleteUser(sessionUser.getUserID());

        //탈퇴한 후 자동 로그아웃
        httpSession.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Void> loginAPI(
            @Valid
            @RequestBody LoginRequestDto loginRequestDto,
            HttpSession httpSession) {

        SessionUser sessionUser = userService.login(loginRequestDto);
        httpSession.setAttribute("loginUser", sessionUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logoutAPI(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            HttpSession httpSession) {

        // 로그인이 되었는지 확인
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        httpSession.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
