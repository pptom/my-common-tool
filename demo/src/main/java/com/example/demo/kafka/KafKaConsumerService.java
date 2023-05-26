package com.example.demo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author ptomjie
 * @since 2023-03-26 10:13
 */
@Service
public class KafKaConsumerService {
    private final Logger logger = LoggerFactory.getLogger(KafKaConsumerService.class);

    @KafkaListener(topics = "${general.topic.name}", groupId = "${general.topic.group.id}")
    public void consume(String message) {
        logger.info(String.format("Message recieved -> %s", message));
    }

    @KafkaListener(topics = "${user.topic.name}",
            groupId = "${user.topic.group.id}",
            containerFactory = "userKafkaListenerContainerFactory")
    public void consumeSecond(String user) {
        logger.info(String.format("User created -> %s", user));
    }
}
