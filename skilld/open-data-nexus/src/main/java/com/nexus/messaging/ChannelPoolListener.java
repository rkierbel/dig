package com.nexus.messaging;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.micronaut.rabbitmq.connect.ChannelInitializer;
import jakarta.inject.Singleton;

import java.io.IOException;

@Singleton
public class ChannelPoolListener extends ChannelInitializer {

    @Override
    public void initialize(Channel channel, String name) throws IOException {
        channel.exchangeDeclare("open-data-exchange", BuiltinExchangeType.DIRECT, true);
        channel.queueDeclare("insee-in", true, false, false, null);
        channel.queueBind("insee-out", "open-data-exchange", "insee-in");
    }
}
