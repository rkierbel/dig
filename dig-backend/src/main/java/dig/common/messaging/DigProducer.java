package dig.common.messaging;

import dig.france.insee.sirene.messaging.event.SireneHistoricizedSearchEvent;
import dig.france.insee.sirene.search.response.SearchReportDto;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient("${rabbitmq.exchange.dig}")
public interface DigProducer {

    @Binding("${rabbitmq.binding.insee.sirene.search-request}")
    void sendSireneHistoricizedSearchEvent(SireneHistoricizedSearchEvent event);

    void sendCompletedSearchEvent(SearchReportDto reportDto);
}
