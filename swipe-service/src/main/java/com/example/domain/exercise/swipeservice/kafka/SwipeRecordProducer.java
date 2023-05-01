package com.example.domain.exercise.swipeservice.kafka;

import com.example.domain.exercise.swipeservice.models.SwipeRecordOutput;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SwipeRecordProducer {

  private static final Logger LOGGER = LoggerFactory.getLogger(SwipeRecordProducer.class);

  @Value(value = "${spring.kafka.topic.swipeRecord}")
  private String topicName;

  @Autowired private KafkaTemplate<String, List<SwipeRecordOutput>> kafkaTemplate;

  public void sendMessage(List<SwipeRecordOutput> swipeRecordOutput) {

    LOGGER.info(String.format("Message Sent %s", swipeRecordOutput));
    Message<List<SwipeRecordOutput>> msg =
        MessageBuilder.withPayload(swipeRecordOutput)
            .setHeader(KafkaHeaders.TOPIC, topicName)
            .build();

    kafkaTemplate.send(msg);
  }
}
