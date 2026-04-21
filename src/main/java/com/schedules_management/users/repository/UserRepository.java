package com.schedules_management.users.repository;

import com.schedules_management.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 유저 이름 유무 확인
    /* Optional wrapper 클래스를 사용 (값이 있을 수도 없을 수도 있는 상황 표현)
     */
    Optional<User> findByName(String name);

    // 유저 필터 없으면 전체조회 OR 있으면 해당 유저 조회
    @Query("SELECT u FROM User u WHERE (:userName IS NULL OR u.name = :userName)")
    List<User> findAllByUserName(@Param("userName") String userName);


}
