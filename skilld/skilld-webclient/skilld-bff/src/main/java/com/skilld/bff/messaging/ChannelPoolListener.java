package com.skilld.bff.messaging;

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

    @Value("${rabbitmq.queue.insee.siren-search}")
    String sirenSearchQueue;

    @Value("${rabbitmq.binding.insee.siren-search}")
    String sirenSearchBinding;

    @Override
    public void initialize(Channel channel, String name) throws IOException {
        channel.exchangeDeclare(inseeExchange, BuiltinExchangeType.DIRECT, true);
        channel.queueDeclare(sirenSearchQueue, true, false, false, null);
        channel.queueBind(sirenSearchQueue, inseeExchange, sirenSearchBinding);
    }
}
