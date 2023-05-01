package com.example.domain.exercise.swipeservice.models;

import com.example.domain.exercise.swipeservice.enums.AttendanceStaus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwipeRecordOutput {
  Long employeeId;
  String employeeName;
  LocalDateTime SwipeOutTime;
  LocalDateTime SwipeInTime;
  Double TotalWorkingHours;
  AttendanceStaus status;
}
