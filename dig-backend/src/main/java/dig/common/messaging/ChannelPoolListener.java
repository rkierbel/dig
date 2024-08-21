package dig.common.messaging;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Value;
import io.micronaut.context.env.Environment;
import io.micronaut.rabbitmq.connect.ChannelInitializer;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Singleton
@Requires(notEnv = Environment.TEST)
@Slf4j
public class ChannelPoolListener extends ChannelInitializer {

    @Value("${rabbitmq.exchange.dig}")
    String digExc;

    @Value("${rabbitmq.queue.insee.sirene.search-request}")
    String sireneSearchRequestQ;
    @Value("${rabbitmq.binding.insee.sirene.search-request}")
    String sireneSearchRequestBinding;

    @Value("${rabbitmq.queue.insee.sirene.search-response}")
    String sireneSearchResponseQ;
    @Value("${rabbitmq.binding.insee.sirene.search-response}")
    String sireneSearchResponseBinding;

    @Value("${rabbitmq.queue.insee.sirene.ping}")
    String sirenePingQ;
    @Value("${rabbitmq.binding.insee.sirene.ping}")
    String sirenePingBinding;


    @Override
    public void initialize(Channel channel, String name) throws IOException {
        log.info("[ChannelPoolListener] Initializing rabbitMQ exchange, queues and bindings");
        channel.exchangeDeclare(digExc, BuiltinExchangeType.DIRECT, true);

        channel.queueDeclare(sireneSearchRequestQ, true, false, false, null);
        channel.queueDeclare(sireneSearchResponseQ, true, false, false, null);
        channel.queueDeclare(sirenePingQ, true, false, false, null);

        channel.queueBind(sireneSearchRequestQ, digExc, sireneSearchRequestBinding);
        channel.queueBind(sireneSearchResponseQ, digExc, sireneSearchResponseBinding);
        channel.queueBind(sirenePingQ, digExc, sirenePingBinding);
        log.info("[ChannelPoolListener] RabbitMQ initialization complete");
    }
}
