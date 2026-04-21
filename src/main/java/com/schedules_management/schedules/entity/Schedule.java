package com.schedules_management.schedules.entity;

import com.schedules_management.users.entity.User;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "schedules")
@EntityListeners(AuditingEntityListener.class)

public class Schedule {
    // 속성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleID;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    /*유저가 탈퇴하여도 일정 DB는 남겨놓아 추후 재가입 및 이전 일정 복구를 원한다면 복구할 수 있도록 한다.
     * JPA는 ID가 아닌 객체로 연결하기 때문에 User객체로 연결한다
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = true) // JPA에서 null 허용 여부
    @JoinColumn(name = "user_id", nullable = true) // DB에서 null 허용 여부
    private User user; // userID가 아닌 User 객체


    // 생성자
    /* JPA 가 활용하는 생성자
     * JPA가 DB에서 데이터를 꺼내올 때 빈 객체를 먼저 만들고 나서 값을 채워넣기 때문에 기본 생성자(파라미터가 없는 생성자)가 반드시 필요.
     * 외부에서 new Schedule() 로 빈 객체를 만드는 것을 막기 위해 protected
     */
    protected Schedule() {}

    public Schedule(User user, String title,String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }


    // 기능
    // getter
    public Long getScheduleID() {
        return scheduleID;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public User getUser() {
        return user;
    }


    // update
    public void update(String title, String content) {
        //null이 아닐 때만 변경, null인 경우 기존 데이터 유지
        if(title != null) this.title = title;
        if(content != null) this.content = content;
    }


}

