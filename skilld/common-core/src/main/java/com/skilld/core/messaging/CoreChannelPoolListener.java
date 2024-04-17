package com.skilld.core.messaging;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Value;
import io.micronaut.context.env.Environment;
import io.micronaut.rabbitmq.connect.ChannelInitializer;
import jakarta.inject.Singleton;

import java.io.IOException;

@Singleton
@Requires(notEnv = Environment.TEST)
public class CoreChannelPoolListener extends ChannelInitializer {

    @Value("${rabbitmq.exchange.insee.sirene}")
    String sireneExc;

    @Value("${rabbitmq.queue.insee.sirene-search}")
    String sireneSearchQ;

    @Value("${rabbitmq.binding.insee.sirene-search}")
    String sireneSearchB;

    @Override
    public void initialize(Channel channel, String name) throws IOException {
        channel.exchangeDeclare(sireneExc, BuiltinExchangeType.DIRECT, true);
        channel.queueDeclare(sireneSearchQ, true, false, false, null);
        channel.queueBind(sireneSearchQ, sireneExc, sireneSearchB);
    }
}
