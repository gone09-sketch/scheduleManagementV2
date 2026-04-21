package com.schedules_management.schedules.service;


import com.schedules_management.schedules.dto.*;
import com.schedules_management.schedules.entity.Schedule;
import com.schedules_management.schedules.repository.ScheduleRepository;
import com.schedules_management.users.entity.User;
import com.schedules_management.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service

public class ScheduleService {
    // 속성
    private ScheduleRepository scheduleRepository;
    private UserRepository userRepository;

    // 생성자
    public ScheduleService(ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    // 기능

    // 일정생성
    @Transactional
    public ScheduleCreateResponseDto createSchedule(Long userID, ScheduleCreateRequestDto createRequestDto) {
        // 1. 유저 확인(검증)
        User user = userRepository.findByUserID(userID).orElseThrow(
                () -> new IndexOutOfBoundsException("존재하지 않는 유저입니다"));

        // 2. 엔티티 객체 생성
        Schedule newSchedule = new Schedule(
                user,
                createRequestDto.getTitle(),
                createRequestDto.getContent()
        );

        // 3. 저장
        Schedule savedSchedule = scheduleRepository.save(newSchedule);

        // dto 객체 생성 후 반환
        ScheduleCreateResponseDto createResponseDto = new ScheduleCreateResponseDto(
                savedSchedule.getScheduleID(),
                savedSchedule.getUser().getName(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getUpdatedAt()
        );
        return createResponseDto;
    }


    // 전체조회
    @Transactional(readOnly = true)
    public List<ScheduleGetResponseDto> getAllSchedule(String userName) {
        // 1. 유저 존재 유무 확인(검증)
            // name이 null 이면 유저 확인 자체 스킵
        if (userName != null) {
            userRepository.findByUserName(userName).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않은 유저입니다."));
        }

        // 2. 일정 목록 조회(데이터 가져오기)
            // name이 null 이면 전체조회
        List<ScheduleGetResponseDto> getAllResponseDto = scheduleRepository.findAllByUserName(userName)
                .stream()
                // dto로 변환
                .map(schedule -> new ScheduleGetResponseDto(
                        schedule.getScheduleID(),
                        schedule.getUser().getName(),
                        schedule.getTitle(),
                        schedule.getCreatedAt(),
                        schedule.getUpdatedAt()
                ))
                // 최종 연산
                .toList();

        return getAllResponseDto;
    }

    // 단건조회
    @Transactional(readOnly = true)
    public ScheduleGetResponseDto getOneSchedule(Long scheduleID) {
        // DB에서 해당 scheduleID 정보 가져오기
        Schedule schedule= scheduleRepository.findById(scheduleID).orElseThrow(
                () -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        ScheduleGetResponseDto getOneResponseDto = new ScheduleGetResponseDto(
                schedule.getScheduleID(),
                schedule.getUser().getName(),
                schedule.getTitle(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
        return getOneResponseDto;
    }

    // 일정수정
    @Transactional
    public SchedulePatchResponseDto patchSchedule(Long userID, Long scheduleID, SchedulePatchRequestDto patchRequestDto) {
        // 1. 유저 유무 확인(검증)
        userRepository.findByUserID(userID).orElseThrow(
                () -> new IndexOutOfBoundsException("존재하지 않는 유저입니다"));

        // 2. DB에서 해당 scheduleID 정보 가져오기
        Schedule updatedSchedule= scheduleRepository.findById(scheduleID).orElseThrow(
                () -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        // 2. 수정내용 반영
        updatedSchedule.update(
                patchRequestDto.getTitle(),
                patchRequestDto.getContent()
        );

        // 3. 반환
        SchedulePatchResponseDto patchResponseDto = new SchedulePatchResponseDto(
                updatedSchedule.getScheduleID(),
                updatedSchedule.getUser().getName(),
                updatedSchedule.getTitle(),
                updatedSchedule.getContent(),
                updatedSchedule.getCreatedAt(),
                updatedSchedule.getUpdatedAt()
        );
        return patchResponseDto;
    }


    // 일정삭제
    @Transactional
    public void deleteSchedule(Long userID, Long scheduleID) {
        // 1. 유저 확인
        User user = userRepository.findByUserID(userID).orElseThrow(
                () -> new IndexOutOfBoundsException("존재하지 않는 유저입니다"));

        // 2. 해당 scheduleID 확인
        scheduleRepository.findById(scheduleID).orElseThrow(
                () -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        // 3. 삭제처리
        scheduleRepository.deleteById(scheduleID);
    }

}
