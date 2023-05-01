package com.example.domain.exercise.swipeservice.kafka;

import com.example.domain.exercise.swipeservice.models.SwipeRecordOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SwipeRecordConsumer {

  // @Autowired AttendanceService attendanceService;

  //  private KafkaTemplate<String, SwipeRecordOutput> kafkaTemplate;

  @KafkaListener(topics = "swipeRecord-topic", groupId = "myGroup")
  public void consume(@Payload List<SwipeRecordOutput> swipeRecordOutput) {
    log.info("Message Received: " + swipeRecordOutput);
    //   attendanceService.saveAttendanceRecord(swipeRecordOutput);
  }
}
