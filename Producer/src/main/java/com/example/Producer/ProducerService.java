package com.example.Producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${java.rabbitmq.exchange}")
    private String exchange;

    @Value("${java.rabbitmq.routingkey}")
    private String routingkey;

    public void send(String msg) {
        amqpTemplate.convertAndSend(exchange, routingkey, msg);
        logger.info("Send msg = " + msg);

    }
}
