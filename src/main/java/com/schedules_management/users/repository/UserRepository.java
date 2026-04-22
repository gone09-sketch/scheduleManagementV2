package com.schedules_management.users.repository;

import com.schedules_management.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 유저 이름 유무 확인(검증)
    /* Optional wrapper 클래스를 사용 (값이 있을 수도 없을 수도 있는 상황 표현)
     * 탈퇴한 유저의 이름을 넣어도 "존재하는 유저"로 찾아짐 -> 유저가 삭제되면 찾지 않기
     */
    @Query("SELECT u FROM User u WHERE u.name = :userName AND u.isDeleted = false")
    Optional<User> findByUserName(@Param("userName") String userName);


    // 유저 ID 유무 확인(검증)
    /* Optional wrapper 클래스를 사용 (값이 있을 수도 없을 수도 있는 상황 표현)
     */
    @Query("SELECT u FROM User u WHERE u.userID = :userID AND u.isDeleted = false")
    Optional<User> findByUserID(@Param("userID") Long userID);


    // 유저 필터 없으면 전체조회 OR 있으면 해당 유저 조회 (탈퇴한 유저 조회X)
    @Query("SELECT u FROM User u WHERE (:userName IS NULL OR u.name = :userName) AND u.isDeleted = false")
    List<User> findAllByUserName(@Param("userName") String userName);

    // 유저 이메일 확인
    @Query("SELECT u FROM User u WHERE u.email = :userEmail AND u.isDeleted = false")
    Optional<User> findByUserEmail(@Param("userEmail") String userEmail);

}
