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

        // 1. 해당 userID로 유저 존재 유무 확인(검증)
        User user = userRepository.findByUserID(userID).orElseThrow(
                () -> new IndexOutOfBoundsException("존재하지 않는 유저입니다"));

        // 2. 엔티티 객체 생성 (+요청 데이터 넣어주기)
        Schedule newSchedule = new Schedule(
                user,
                createRequestDto.getTitle(),
                createRequestDto.getContent()
        );

        // 3. 저장 + 저장된 entity의 데이터 담기
        Schedule savedSchedule = scheduleRepository.save(newSchedule);

        // 4. dto 객체 생성(+저장된 데이터 넣기)
        ScheduleCreateResponseDto createResponseDto = new ScheduleCreateResponseDto(
                savedSchedule.getScheduleID(),
                savedSchedule.getUser().getName(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getUpdatedAt()
        );

        // 5. 반환
        return createResponseDto;
    }


    // 전체조회
    @Transactional(readOnly = true)
    public List<ScheduleGetResponseDto> getAllSchedule(String userName) {

        // 1. 해당 userName으로 유저 존재 유무 확인(검증)
            // name이 null 이면 유저 확인 자체 스킵
        if (userName != null) {
            userRepository.findByUserName(userName).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않은 유저입니다."));
        }

        // 2. 일정 목록 조회(해당 유저의 일정 가져오기)
            // name이 null 이면 전체 일정 가지고 오기
            // name이 null 이 아니면 해당 유저의 일정 가지고 오기
        List<ScheduleGetResponseDto> getAllResponseDto = scheduleRepository.findAllByUserName(userName)
                .stream()
                // dto 변환 (객체 생성 및 가지고 온 데이터 넣기)
                .map(schedule -> new ScheduleGetResponseDto(
                        schedule.getScheduleID(),
                        schedule.getUser().getName(),
                        schedule.getTitle(),
                        schedule.getCreatedAt(),
                        schedule.getUpdatedAt()
                ))
                // 최종 연산
                /* 조회만 할 것이기 때문에(리스트 수정X) .toList() 로 연산 마무리 */
                .toList();

         // 3. 반환
        return getAllResponseDto;
    }


    // 단건조회
    @Transactional(readOnly = true)
    public ScheduleGetResponseDto getOneSchedule(Long scheduleID) {

        // 1. 해당 scheduleID로 일정 존재 유무 확인(검증) + 데이터 가져와서 담기
        Schedule schedule= scheduleRepository.findById(scheduleID).orElseThrow(
                () -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        // 2. dto 객체 생성 (+해당 일정 데이터 받기)
        ScheduleGetResponseDto getOneResponseDto = new ScheduleGetResponseDto(
                schedule.getScheduleID(),
                schedule.getUser().getName(),
                schedule.getTitle(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );

        // 3. 반환
        return getOneResponseDto;
    }


    // 일정수정
    @Transactional
    public SchedulePatchResponseDto patchSchedule(Long userID, Long scheduleID, SchedulePatchRequestDto patchRequestDto) {
        // 1. 해당 userID로 유저 존재 유무 확인(검증)
        userRepository.findByUserID(userID).orElseThrow(
                () -> new IndexOutOfBoundsException("존재하지 않는 유저입니다"));

        // 2. 해당 scheduleID로 일정 존재 유무 확인(검증) + 데이터 가져와서 담기
        Schedule updatedSchedule= scheduleRepository.findById(scheduleID).orElseThrow(
                () -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        // 2. 수정내용 Schedule 엔티티에 반영
        updatedSchedule.update(
                patchRequestDto.getTitle(),
                patchRequestDto.getContent()
        );

        // 3. dto 객체 생성 (+ 데이터 담기)
        SchedulePatchResponseDto patchResponseDto = new SchedulePatchResponseDto(
                updatedSchedule.getScheduleID(),
                updatedSchedule.getUser().getName(),
                updatedSchedule.getTitle(),
                updatedSchedule.getContent(),
                updatedSchedule.getCreatedAt(),
                updatedSchedule.getUpdatedAt()
        );

        // 4. 반환
        return patchResponseDto;
    }


    // 일정삭제
    @Transactional
    public void deleteSchedule(Long userID, Long scheduleID) {
        // 1. 해당 userID로 유저 존재 유무 확인(검증)
        userRepository.findByUserID(userID).orElseThrow(
                () -> new IndexOutOfBoundsException("존재하지 않는 유저입니다"));

        // 2. 해당 scheduleID 확인
        scheduleRepository.findById(scheduleID).orElseThrow(
                () -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        // 3. 삭제처리
        scheduleRepository.deleteById(scheduleID);
    }

}
