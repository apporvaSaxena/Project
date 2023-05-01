package com.example.domain.exercise.swipeservice.service;

import com.example.domain.exercise.swipeservice.models.EmployeeSwipeRequest;
import com.example.domain.exercise.swipeservice.repository.SwipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;

@DataMongoTest
@EnableAutoConfiguration
@ContextConfiguration(
    classes = {SwipeRepository.class, SwipeServiceImpl.class, EmployeeSwipeRequest.class})
class SwipeServiceImplTest {

  @Autowired private SwipeServiceImpl SwipeServiceImpl;

  @Test
  void findDistinctEmployees() {
    // List emp = SwipeServiceImpl.findDistinctEmployeeIds();
    // Assertions.assertNotNull(emp);
  }
}
