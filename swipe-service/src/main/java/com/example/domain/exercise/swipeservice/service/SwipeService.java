package com.example.domain.exercise.swipeservice.service;

import com.example.domain.exercise.swipeservice.models.EmployeeSwipeRequest;
import com.example.domain.exercise.swipeservice.models.SwipeRecordOutput;

import java.text.ParseException;
import java.util.List;

public interface SwipeService {
  public EmployeeSwipeRequest saveSwipeRecord(EmployeeSwipeRequest employeeSwipeRequest);

  public List<SwipeRecordOutput> getAttendanceData(String date) throws ParseException;

  //	public List<SwipeInfo> getSwipesByEmployeeId(Long userId);
  //	public List<SwipeInfo> getSwipesForTodayByEmployeeId(Long userId);
  //	public List<SwipeInfo> getSwipesForDay();
  //	public void calculateInOfficeHourAndPublishEmployeeStateToKafkaTopic();
}
