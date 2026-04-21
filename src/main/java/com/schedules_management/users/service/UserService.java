package com.schedules_management.users.service;


import com.schedules_management.schedules.repository.ScheduleRepository;
import com.schedules_management.users.dto.*;
import com.schedules_management.users.entity.User;
import com.schedules_management.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class UserService {
    // 속성
    private UserRepository userRepository;
    private ScheduleRepository scheduleRepository;

    // 생성자
    public UserService(UserRepository userRepository, ScheduleRepository scheduleRepository) {
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
    }


    // 기능
    // 유저생성(회원가입)
    @Transactional
    public UserCreateResponseDto createUser(UserCreateRequestDto createRequestDto) {
        // 엔티티 생성
        User newUser = new User(
                createRequestDto.getName(),
                createRequestDto.getEmail(),
                createRequestDto.getPassword()
        );

        // 저장
        User savedUser = userRepository.save(newUser);

        // dto 객체 생성 및 반환
        UserCreateResponseDto createResponseDto = new UserCreateResponseDto(
                savedUser.getUserID(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getUpdatedAt()
        );
        return createResponseDto;
    }


    // 유저 전체조회 (이름 조회 가능)
    @Transactional(readOnly = true)
    public List<UserGetResponseDto> getAllUser(String userName) {
        // 유저 존재 유무 확인(검증)
        if (userName != null) {
            userRepository.findByUserName(userName).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않은 유저입니다."));
        }

        // 해당 유저 이름 정보 OR 유저 이름 없다면 전체 유저 목록 가져오기 (DB에서 필터링 된 데이터 가져오기)
        List<UserGetResponseDto> getAllResponseDto = userRepository.findAllByUserName(userName)
                .stream()
                // dto로 변환
                .map(user -> new UserGetResponseDto(
                        user.getUserID(),
                        user.getName(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getUpdatedAt()
                ))
                // 최종 연산
                .toList();

        return getAllResponseDto;
    }


    // 유저 단건조회
    @Transactional(readOnly = true)
    public UserGetResponseDto getOneUser(Long userID) {

        // 해당 userID 유무 확인(검증) + 데이터 가져오기
        User user = userRepository.findByUserID(userID).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // dto 객체 생성 및 반환
        UserGetResponseDto getResponseDto = new UserGetResponseDto(
                user.getUserID(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
        return getResponseDto;
    }


    // 유저수정
    @Transactional
    public UserPatchResponseDto patchUser(Long userID, UserPatchRequestDto patchRequestDto) {

        // 해당 userID 유무 확인(검증) + 데이터 가져오기
        User user = userRepository.findByUserID(userID).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // 수정된 내용 반영
        user.update(
                patchRequestDto.getEmail(),
                patchRequestDto.getPassword()
        );

        // dto 객체 생성 및 반환
        UserPatchResponseDto patchResponseDto = new UserPatchResponseDto(
                user.getUserID(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
        return patchResponseDto;
    }

    // 유저삭제
    @Transactional
    public void deleteUser(Long userID) {

        // 해당 userID 유무 확인(검증) + 데이터 담아주기
        User user = userRepository.findByUserID(userID).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // 일정의 user_id null 처리
        scheduleRepository.clearUserFromSchedule(userID);

        // 유저 삭제처리 (soft delete)
        user.isDeleted();
    }
}
