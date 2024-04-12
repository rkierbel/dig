package com.skilld.core.messaging;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.micronaut.context.annotation.Value;
import io.micronaut.rabbitmq.connect.ChannelInitializer;
import jakarta.inject.Singleton;

import java.io.IOException;

@Singleton
public class ChannelPoolListener extends ChannelInitializer {

    @Value("${rabbitmq.exchange.insee}")
    String inseeExchange;

    @Value("${rabbitmq.queue.insee.sirene-search}")
    String sireneSearchQueue;

    @Value("${rabbitmq.binding.insee.sirene-search}")
    String sireneSearchBinding;

    @Override
    public void initialize(Channel channel, String name) throws IOException {
        channel.exchangeDeclare(inseeExchange, BuiltinExchangeType.DIRECT, true);
        channel.queueDeclare(sireneSearchQueue, true, false, false, null);
        channel.queueBind(sireneSearchQueue, inseeExchange, sireneSearchBinding);
    }
}
