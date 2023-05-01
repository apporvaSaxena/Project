package com.example.domain.exercise.swipeservice.service;

import com.example.domain.exercise.swipeservice.enums.AttendanceStaus;
import com.example.domain.exercise.swipeservice.kafka.SwipeRecordProducer;
import com.example.domain.exercise.swipeservice.models.EmployeeSwipeRequest;
import com.example.domain.exercise.swipeservice.models.SwipeRecordOutput;
import com.example.domain.exercise.swipeservice.repository.SwipeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
public class SwipeServiceImpl implements SwipeService {

  private static final Logger LOG = LoggerFactory.getLogger(SwipeServiceImpl.class);

  @Autowired private SwipeRepository swipeRepository;

  @Autowired MongoTemplate mongoTemplate;

  //  public SwipeServiceImpl(SwipeRepository swipeRepository) {
  //    this.swipeRepository = swipeRepository;
  //  }

  @Autowired private SwipeRecordProducer swipeRecordProducer;

  @Autowired private ObjectMapper mapper;

  //  @Autowired private EmployeeProfileServiceCaller employeeProfileServiceCaller;

  @Override
  public EmployeeSwipeRequest saveSwipeRecord(EmployeeSwipeRequest employeeSwipeRequest) {
    var swipeTime = employeeSwipeRequest.getSwipeTime();
    if (Objects.isNull(swipeTime)) {
      employeeSwipeRequest.setSwipeTime(LocalDateTime.now());
    }
    return swipeRepository.save(employeeSwipeRequest);
  }

  public void publishAttendanceData() throws ParseException {

    var attendanceData = getAttendanceData("2023-04-19");
    //   var attendanceData = getAttendanceData(String.valueOf(date));
    publishToKafkaTopic(attendanceData);
  }

  public List<SwipeRecordOutput> getAttendanceData(String date) throws ParseException {
    String startDate = date.concat("T00:00:00Z");
    String endDate = date.concat("T23:59:59Z");
    // Create a DateTimeFormatter object for parsing the ISO date
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    // Parse the ISO date string into a LocalDate object
    LocalDateTime isoStartDate = LocalDateTime.parse(startDate, formatter);
    LocalDateTime isoEndDate = LocalDateTime.parse(endDate, formatter);

    List<Aggregation> stages = new ArrayList<>();
    MatchOperation matchOperation =
        Aggregation.match(Criteria.where("swipeTime").gte(isoStartDate).lt(isoEndDate));
    GroupOperation groupOperation =
        Aggregation.group("employeeId", "employeeName")
            .max("swipeTime")
            .as("SwipeOutTime")
            .min("swipeTime")
            .as("SwipeInTime");

    ProjectionOperation projectionOperation =
        Aggregation.project("employeeId", "employeeName", "SwipeOutTime", "SwipeInTime")
            .andExpression("(SwipeOutTime - SwipeInTime)")
            .divide(3600000L)
            .as("TotalWorkingHours");

    var aggregatedData1 = newAggregation(matchOperation, groupOperation, projectionOperation);
    AggregationResults<SwipeRecordOutput> aggregatedData =
        mongoTemplate.aggregate(
            newAggregation(matchOperation, groupOperation, projectionOperation),
            "EmployeeSwipeRequest",
            SwipeRecordOutput.class);

    var swipeRecordOutput = aggregatedData.getMappedResults();
    var swipeRecordOutput1 =
        swipeRecordOutput.stream()
            .map(
                swipeRecordOutput2 -> {
                  var workingHours = swipeRecordOutput2.getTotalWorkingHours();
                  var status = determineUserStatus(workingHours);
                  swipeRecordOutput2.setStatus(status);
                  return swipeRecordOutput2;
                })
            .collect(Collectors.toList());
    System.out.println("mappedData: " + swipeRecordOutput1);
    return swipeRecordOutput1;
  }

  private AttendanceStaus determineUserStatus(Double inOfficeHour) {
    return inOfficeHour < 4
        ? AttendanceStaus.ABSENT
        : ((inOfficeHour >= 4 && inOfficeHour < 8)
            ? AttendanceStaus.HALF_DAY
            : AttendanceStaus.PRESENT);
  }

  private void publishToKafkaTopic(List<SwipeRecordOutput> swipeRecordOutput) {
    swipeRecordProducer.sendMessage(swipeRecordOutput);
    LOG.info("publishToKafkaTopic" + swipeRecordOutput);
  }
}
