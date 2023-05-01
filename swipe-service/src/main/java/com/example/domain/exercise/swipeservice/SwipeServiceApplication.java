package com.example.domain.exercise.swipeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SwipeServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(SwipeServiceApplication.class, args);
  }
}
