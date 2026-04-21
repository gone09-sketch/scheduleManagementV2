package com.schedules_management.users.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)

public class User {
    // 속성
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    // 생성자
    /* JPA 가 활용하는 생성자
     * JPA가 DB에서 데이터를 꺼내올 때 빈 객체를 먼저 만들고 나서 값을 채워넣기 때문에 기본 생성자(파라미터가 없는 생성자)가 반드시 필요.
     * 외부에서 new User() 로 빈 객체를 만드는 것을 막기 위해 protected
     */
    protected User() {}

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // 기능
    // getter
    public Long getUserID() {
        return userID;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // update
    public void update(String email, String password) {
        //null이 아닐 때만 변경, null인 경우 기존 데이터 유지
        if(email != null) this.email = email;
        if(password != null) this.password = password;
    }

}
