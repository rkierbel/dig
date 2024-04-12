package com.skilld.bff.messaging;

import com.skilld.bff.insee.SireneSearchEvent;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient("sirene")
public interface SireneProducer {

    @Binding("${rabbitmq.binding.insee.sirene-search")
    void sendSireneSearchEvent(SireneSearchEvent event);
}
