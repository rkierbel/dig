package dig.common.messaging;

import dig.common.messaging.event.SireneHistoricizedSearchEvent;
import dig.france.insee.sirene.search.result.SireneSearchResponse;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient("${rabbitmq.exchange.dig}")
public interface DigProducer {

    @Binding("${rabbitmq.binding.insee.sirene.search-request}")
    void sendSireneHistoricizedSearchEvent(SireneHistoricizedSearchEvent event);

    @Binding("${rabbitmq.binding.insee.sirene.search-response}")
    void onSireneSearchResponse(SireneSearchResponse sireneSearchResponse);
}
