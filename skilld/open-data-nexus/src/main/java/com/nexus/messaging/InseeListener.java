package com.nexus.messaging;

import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;

@RabbitListener
public class InseeListener {

    @Queue("${rabbitmq.queue.insee}")
    public void receive(String message) {
        System.out.println(message);
    }
}
