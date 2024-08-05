package common.messaging;

import common.messaging.event.SireneSearchEvent;
import france.insee.sirene.search.SireneSearchResponse;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient("${rabbitmq.exchange.dig}")
@Requires(notEnv = Environment.TEST)
public interface DigProducer {

    @Binding("${rabbitmq.binding.insee.sirene.search-request}")
    void sendSireneSearchRequestEvent(SireneSearchEvent event);

    @Binding("${rabbitmq.binding.insee.sirene.search-response}")
    void onSireneSearchResponse(SireneSearchResponse sireneSearchResponse);
}
