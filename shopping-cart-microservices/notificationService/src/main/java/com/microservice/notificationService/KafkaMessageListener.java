package com.microservice.notificationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListener {


    private static final String TOPIC = "micro1-order-service-t1";
    Logger logger = LoggerFactory.getLogger(KafkaMessageListener.class);

    @KafkaListener(topics = TOPIC, groupId = "notification-group1")
    public void consume(String message){
        logger.info("consumer consumed the message user : {}", message); // print the consumed message from topic to console
    }

}