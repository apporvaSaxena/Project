package com.example.domain.exercise.swipeservice.models;

import com.example.domain.exercise.swipeservice.enums.SwipeType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "EmployeeSwipeRequest")
public class EmployeeSwipeRequest {

  @MongoId private ObjectId systemId;
  private Long employeeId;
  private String employeeName;
  private SwipeType swipeType;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
  private LocalDateTime swipeTime;
}
