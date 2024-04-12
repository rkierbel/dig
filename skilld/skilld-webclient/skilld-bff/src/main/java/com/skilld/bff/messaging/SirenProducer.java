package com.skilld.bff.messaging;

import com.skilld.bff.insee.SirenSearchEvent;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient("siren")
public interface SirenProducer {

    @Binding("${rabbitmq.binding.insee.siren-search")
    void sendSirenSearchEvent(SirenSearchEvent event);
}
