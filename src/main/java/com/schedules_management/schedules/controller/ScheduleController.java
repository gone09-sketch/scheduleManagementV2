package com.schedules_management.schedules.controller;

import com.schedules_management.schedules.dto.*;
import com.schedules_management.schedules.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping

public class ScheduleController {
    // 속성
    private ScheduleService scheduleService;

    // 생성자
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 기능

    // 일정생성
    @PostMapping("/users/{userID}/schedules")
    public ResponseEntity<ScheduleCreateResponseDto> createScheduleAPI(
            @PathVariable("userID") Long userID,
            @RequestBody ScheduleCreateRequestDto createRequestDto) {

        ScheduleCreateResponseDto createResponseAPI = scheduleService.createSchedule(userID, createRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponseAPI);
    }

    // 전체조회 (일정 목록 조회)
    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleGetResponseDto>> getAllScheduleAPI(
            @RequestParam(required = false) String userName) {

        List<ScheduleGetResponseDto> getAllResponseAPI = scheduleService.getAllSchedule(userName);
        return ResponseEntity.status(HttpStatus.OK).body(getAllResponseAPI);
    }

    // 단건조회
    @GetMapping("/schedules/{scheduleID}")
    public ResponseEntity<ScheduleGetResponseDto> getOneScheduleAPI(
            @PathVariable("scheduleID") Long scheduleID) {

        ScheduleGetResponseDto getOneResponseAPI = scheduleService.getOneSchedule(scheduleID);
        return ResponseEntity.status(HttpStatus.OK).body(getOneResponseAPI);
    }

    // 일정수정
    @PatchMapping("/users/{userID}/schedules/{scheduleID}")
    public ResponseEntity<SchedulePatchResponseDto> patchScheduleAPI(
            @PathVariable("userID") Long userID,
            @PathVariable("scheduleID") Long scheduleID,
            @RequestBody SchedulePatchRequestDto patchRequestDto) {

        SchedulePatchResponseDto patchResponseDto = scheduleService.patchSchedule(userID, scheduleID, patchRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(patchResponseDto);
    }

    // 일정삭제
    @DeleteMapping("/users/{userID}/schedules/{scheduleID}")
    public ResponseEntity<Void> deleteScheduleAPI(
            @PathVariable("userID") Long userID,
            @PathVariable("scheduleID") Long scheduleId) {

        scheduleService.deleteSchedule(userID, scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
