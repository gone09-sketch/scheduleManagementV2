package com.schedules_management.schedules.repository;

import com.schedules_management.schedules.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // 유저이름으로 일정 찾기
    /* 탈퇴한 유저의 일정은 제외 & 파라미터에 유저이름이 없다면 전체조회/유저이름이 있다면 해당 유저의 일정만 필터링
     */
    @Query("SELECT s FROM Schedule s WHERE s.user IS NOT NULL AND (:userName IS NULL OR s.user.name = :userName)")
    List<Schedule> findAllByUserName(@Param("userName") String userName);

    // 유저 null 처리
    /* 1. Schedule 엔티티를 업데이트 할 때 (회원 탈퇴 시, 일정 테이블의 user_id도 업데이트 됨)
       2. 입력받은 userID를 가진 유저의 일정을 찾아서 (WHERE s.user.userID = :userID)
       3. user 필드를 null 로 바꾼다 (SET s.user = null) = user_id 를 null 로 바꿈 (schedule테이블 수정)
       => 호출 시점이 유저 삭제(탈퇴)하는 시점이므로 탈퇴한 유저의 일정만 처리된다.
     */
    @Modifying // SELECT 가 아닌 UPDATE/DELETE 쿼리를 쓸 때 필요한 어노테이션
    @Query("UPDATE Schedule s SET s.user = null WHERE s.user.userID = :userID")
    void clearUserFromSchedule(@Param("userID") Long userID);

}
