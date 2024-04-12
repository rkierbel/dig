package com.skilld.core.messaging;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Value;
import io.micronaut.rabbitmq.connect.ChannelInitializer;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Singleton
@Slf4j
@Primary
public class ChannelPoolListener extends ChannelInitializer {

    @Value("${rabbitmq.exchange.insee.sirene}")
    String inseeExchange;

    @Value("${rabbitmq.queue.insee.sirene-search}")
    String sireneSearchQueue;

    @Value("${rabbitmq.binding.insee.sirene-search}")
    String sireneSearchBinding;

    @Override
    public void initialize(Channel channel, String name) throws IOException {
        log.info("Beginning channel initialization for exchange {}, queue {} and binding {}",
                inseeExchange, sireneSearchQueue, sireneSearchBinding);
        channel.exchangeDeclare(inseeExchange, BuiltinExchangeType.DIRECT, true);
        channel.queueDeclare(sireneSearchQueue, true, false, false, null);
        channel.queueBind(sireneSearchQueue, inseeExchange, sireneSearchBinding);
        log.info("Completed channel initialization for exchange {}, queue {} and binding {}",
                inseeExchange, sireneSearchQueue, sireneSearchBinding);
    }
}
