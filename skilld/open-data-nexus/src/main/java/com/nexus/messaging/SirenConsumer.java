package com.nexus.messaging;

import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;

@RabbitListener
public class SirenConsumer {

    @Queue("${rabbitmq.queue.insee.sirene-search}")
    void onSireneSearchEvent(SireneSearchEvent sireneSearchEvent) {
        System.out.println(sireneSearchEvent.toString());
    }
}
