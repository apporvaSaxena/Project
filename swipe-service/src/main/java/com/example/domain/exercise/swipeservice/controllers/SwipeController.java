package com.example.domain.exercise.swipeservice.controllers;

import com.example.domain.exercise.swipeservice.models.EmployeeSwipeAckResponse;
import com.example.domain.exercise.swipeservice.models.EmployeeSwipeRequest;
import com.example.domain.exercise.swipeservice.repository.SwipeRepository;
import com.example.domain.exercise.swipeservice.service.ScheduleImpl;
import com.example.domain.exercise.swipeservice.service.SwipeService;
import com.example.domain.exercise.swipeservice.service.SwipeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class SwipeController {

  @Autowired private ObjectMapper mapper;

  @Autowired ScheduleImpl scheduleImpl;

  @Autowired SwipeServiceImpl swipeServiceImpl;

  //  @Autowired private SwipeRecordProducer swipeRecordProducer;
  @Autowired private SwipeService swipeService;

  @Autowired private SwipeRepository swipeRepository;

  @PostMapping("api/employee/swipe/")
  public ResponseEntity<EmployeeSwipeAckResponse> swipeInSwipeOutRecord(
      @RequestBody EmployeeSwipeRequest employeeSwipeRequest) throws ParseException {
    //  LocalDate date = LocalDate.parse("2023-04-19", DateTimeFormatter.ofPattern("YYYY-MM-DD"));
    swipeServiceImpl.publishAttendanceData();

    //   swipeService.saveSwipeRecord(employeeSwipeRequest);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new EmployeeSwipeAckResponse("Recorded Successfully"));
  }

  public String Schedule() throws ParseException {
    scheduleImpl.scheduledJob();
    return "Data published";
  }
}
