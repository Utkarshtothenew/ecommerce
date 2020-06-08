package com.example.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues="${java.rabbitmq.queue}")
    public void recievedMessage(String msg) {
        logger.info("Recieved Message: " + msg);
    }
}
