package com.skilld.bff.messaging;

import com.skilld.bff.insee.SireneSearchEvent;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.rabbitmq.connect.ChannelInitializer;
import jakarta.inject.Inject;

@RabbitClient("${rabbitmq.exchange.insee.sirene}")
@Requires(notEnv = Environment.TEST)
public interface SireneProducer {

    @Binding("${rabbitmq.binding.insee.sirene-search}")
    void sendSireneSearchEvent(SireneSearchEvent event);
}
