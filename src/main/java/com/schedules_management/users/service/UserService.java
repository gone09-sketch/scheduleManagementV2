package com.schedules_management.users.service;


import com.schedules_management.session.LoginRequestDto;
import com.schedules_management.schedules.repository.ScheduleRepository;
import com.schedules_management.session.SessionUser;
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
        // 1. 동일 이메일이 있는지 확인(중복회원가입) (검증)
        if (userRepository.existsByEmail(createRequestDto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 2. 엔티티 객체 생성 (+요청 데이터 넣어주기)
        User newUser = new User(
                createRequestDto.getName(),
                createRequestDto.getEmail(),
                createRequestDto.getPassword()
        );

        // 3. 저장 + 저장된 entity의 데이터 담기
        User savedUser = userRepository.save(newUser);

        // 4. dto 객체 생성(+저장된 데이터 넣기)
        UserCreateResponseDto createResponseDto = new UserCreateResponseDto(
                savedUser.getUserID(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getUpdatedAt()
        );

        // 5. 반환
        return createResponseDto;
    }


    // 유저 전체조회 (이름 조회 가능)
    @Transactional(readOnly = true)
    public List<UserGetResponseDto> getAllUser(String userName) {
        // 1. 해당 userName으로 유저 존재 유무 확인(검증)
            // name이 null 이면 유저 확인 자체 스킵
        if (userName != null) {
            userRepository.findByUserName(userName).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않은 유저입니다."));
        }

        // 2. 유저 목록 조회(해당 유저의 정보 가져오기)
            // name이 null 이면 전체 유저 목록 가지고 오기
            // name이 null 이 아니면 해당 유저의 정보 가지고 오기
        List<UserGetResponseDto> getAllResponseDto = userRepository.findAllByUserName(userName)
                .stream()
                // dto 변환 (객체 생성 및 가지고 온 데이터 넣기)
                .map(user -> new UserGetResponseDto(
                        user.getUserID(),
                        user.getName(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getUpdatedAt()
                ))
                // 최종 연산
                /* 조회만 할 것이기 때문에(리스트 수정X) .toList() 로 연산 마무리 */
                .toList();

        //3. 반환
        return getAllResponseDto;
    }


    // 유저 단건조회
    @Transactional(readOnly = true)
    public UserGetResponseDto getOneUser(Long userID) {

        // 1. 해당 userID로 유저 존재 유무 확인(검증) + 데이터 가져오기
        User user = userRepository.findByUserID(userID).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // 2. dto 객체 생성(+가져온 데이터 넣기)
        UserGetResponseDto getResponseDto = new UserGetResponseDto(
                user.getUserID(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );

        // 3. 반환
        return getResponseDto;
    }


    // 유저수정(이메일 및 비밀번호 수정 가능)
    @Transactional
    public UserPatchResponseDto patchUser(Long sessionUserID, UserPatchRequestDto patchRequestDto) {

        // 1. 해당 userID로 유저 존재 유무 확인(검증) + 데이터 가져오기
        User user = userRepository.findByUserID(sessionUserID).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // 2. 수정된 내용 User 엔티티에 반영
        user.update(
                patchRequestDto.getEmail(),
                patchRequestDto.getPassword()
        );

        // 2. dto 객체 생성(+수정된 데이터 넣기)
        UserPatchResponseDto patchResponseDto = new UserPatchResponseDto(
                user.getUserID(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );

        // 3. 반환
        return patchResponseDto;
    }


    // 유저삭제(회원탈퇴)
    @Transactional
    public void deleteUser(Long sessionUserID) {

        // 1. 해당 userID로 유저 존재 유무 확인(검증) + 데이터 가져오기
        User user = userRepository.findByUserID(sessionUserID).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // 2. 일정의 user_id null 처리
        scheduleRepository.clearUserFromSchedule(sessionUserID);

        // 3. 유저 삭제처리 (soft delete)
        user.delete();
    }


    // 로그인
    @Transactional(readOnly = true)
    public SessionUser login(LoginRequestDto loginRequestDto) {
        // 1. 이메일로 유저 유무 확인 (검증)
        User user = userRepository.findByUserEmail(loginRequestDto.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));

        // 2. 비밀번호 일치 확인 (검증)
        if (!user.getPassword().equals(loginRequestDto.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        // 3. 새 SessionUser 객체에 userID와 email 저장
        SessionUser newSessionUser = new SessionUser(
                user.getUserID(),
                user.getEmail()
        );

        // 4. 반환해주기
        return newSessionUser;
    }



}
