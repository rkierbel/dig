package common.messaging;

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

    @Value("${rabbitmq.exchange.dig}")
    String digExc;

    @Value("${rabbitmq.queue.insee.sirene-search}")
    String sireneSearchQ;

    @Value("${rabbitmq.binding.insee.sirene-search}")
    String sireneSearchBinding;

    @Override
    public void initialize(Channel channel, String name) throws IOException {
        channel.exchangeDeclare(digExc, BuiltinExchangeType.DIRECT, true);
        channel.queueDeclare(sireneSearchQ, true, false, false, null);
        channel.queueBind(sireneSearchQ, digExc, sireneSearchBinding);
    }
}
