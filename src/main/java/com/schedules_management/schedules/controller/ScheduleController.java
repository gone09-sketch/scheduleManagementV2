package com.schedules_management.schedules.controller;

import com.schedules_management.schedules.dto.*;
import com.schedules_management.schedules.service.ScheduleService;
import com.schedules_management.session.SessionUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")

public class ScheduleController {
    // 속성
    private ScheduleService scheduleService;

    // 생성자
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 기능

    // 일정생성
    @PostMapping
    public ResponseEntity<ScheduleCreateResponseDto> createScheduleAPI(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @RequestBody ScheduleCreateRequestDto createRequestDto) {

        // 로그인이 되었는지 확인
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        ScheduleCreateResponseDto createResponseAPI = scheduleService.createSchedule(sessionUser.getUserID(), createRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponseAPI);
    }

    // 전체조회 (일정 목록 조회)
    @GetMapping
    public ResponseEntity<List<ScheduleGetResponseDto>> getAllScheduleAPI(
            @RequestParam(required = false) String userName) {

        List<ScheduleGetResponseDto> getAllResponseAPI = scheduleService.getAllSchedule(userName);
        return ResponseEntity.status(HttpStatus.OK).body(getAllResponseAPI);
    }

    // 단건조회
    @GetMapping("/{scheduleID}")
    public ResponseEntity<ScheduleGetResponseDto> getOneScheduleAPI(
            @PathVariable("scheduleID") Long scheduleID) {

        ScheduleGetResponseDto getOneResponseAPI = scheduleService.getOneSchedule(scheduleID);
        return ResponseEntity.status(HttpStatus.OK).body(getOneResponseAPI);
    }

    // 일정수정
    @PatchMapping("/{scheduleID}")
    public ResponseEntity<SchedulePatchResponseDto> patchScheduleAPI(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable("scheduleID") Long scheduleID,
            @RequestBody SchedulePatchRequestDto patchRequestDto) {

        // 로그인이 되었는지 확인
        if(sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        SchedulePatchResponseDto patchResponseDto = scheduleService.patchSchedule(sessionUser.getUserID(), scheduleID, patchRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(patchResponseDto);
    }

    // 일정삭제
    @DeleteMapping("/{scheduleID}")
    public ResponseEntity<Void> deleteScheduleAPI(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable("scheduleID") Long scheduleId) {

        // 로그인이 되었는지 확인
        if(sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        scheduleService.deleteSchedule(sessionUser.getUserID(), scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
