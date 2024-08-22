package dig.common.messaging;

import dig.france.insee.sirene.messaging.event.SireneSearchCompletedEvent;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.rabbitmq.annotation.RabbitProperty;

@RabbitClient("${rabbitmq.exchange.dig}")
public interface DigProducer {

    @Binding("${rabbitmq.binding.insee.sirene.search-response}")
    void sendCompletedSireneSearchEvent(SireneSearchCompletedEvent event);

    @Binding("${rabbitmq.binding.insee.sirene.ping}")
    @RabbitProperty(name = "contentType", value = "application/json")
    void sendPing(HealthCheckEvent event);
}
