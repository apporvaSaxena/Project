package com.example.domain.exercise.swipeservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class ScheduleImpl {

  @Autowired SwipeServiceImpl swipeServiceImpl;

  @Scheduled(cron = "*/50 * * * * *")
  public void scheduledJob() throws ParseException {
    // LocalDate date = LocalDate.now();
    //  swipeServiceImpl.publishAttendanceData();
  }
}
