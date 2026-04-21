package com.schedules_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SchedulesManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulesManagementApplication.class, args);
    }

}
