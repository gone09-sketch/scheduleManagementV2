package com.schedules_management.schedules.repository;

import com.schedules_management.schedules.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // 유저 필터 없으면 전체조회 OR 있으면 해당 유저 일정 목록 조회
    @Query("SELECT s FROM Schedule s JOIN s.user u WHERE (:userName IS NULL OR u.name = :userName)")
    List<Schedule> findAllByUserName(@Param("userName") String userName);

}
