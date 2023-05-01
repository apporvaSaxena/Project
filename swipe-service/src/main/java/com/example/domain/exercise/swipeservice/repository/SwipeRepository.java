package com.example.domain.exercise.swipeservice.repository;

import com.example.domain.exercise.swipeservice.models.EmployeeSwipeRequest;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SwipeRepository extends MongoRepository<EmployeeSwipeRequest, ObjectId> {

  // List Of distinct Employees(date)
  @Query("{swipeTime : { $gt : ?0}}")
  List<EmployeeSwipeRequest> findDistinctEmployeeIdBySwipeTime(LocalDateTime startTime);

  // swipeInFirst(empId, date)

  // swipeOutLast(empId)

}
