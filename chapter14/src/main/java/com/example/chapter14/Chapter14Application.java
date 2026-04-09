package com.example.chapter14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Chapter14Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter14Application.class, args);
    }

}
