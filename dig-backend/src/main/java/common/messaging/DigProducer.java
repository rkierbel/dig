package common.messaging;

import common.messaging.event.SireneHistoricizedSearchEvent;
import france.insee.sirene.search.SireneSearchResponse;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient("${rabbitmq.exchange.dig}")
public interface DigProducer {

    @Binding("${rabbitmq.binding.insee.sirene.search-request}")
    void sendSireneHistoricizedSearchEvent(SireneHistoricizedSearchEvent event);

    @Binding("${rabbitmq.binding.insee.sirene.search-response}")
    void onSireneSearchResponse(SireneSearchResponse sireneSearchResponse);
}
