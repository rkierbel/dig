package com.nexus.messaging;

import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;

@RabbitListener
public class SirenConsumer {

    @Queue("${rabbitmq.queue.insee.siren-search-q}")
    void onSirenSearchEvent(SirenSearchEvent sirenSearchEvent) {
        System.out.println(sirenSearchEvent.toString());
    }
}
