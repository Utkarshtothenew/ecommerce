package com.example.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;


public class RabbitMQMessageListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQMessageListener.class);


    @Override
    public void onMessage(Message message) {
        logger.info("message = [" + new String(message.getBody()) + "]" );

    }
}
