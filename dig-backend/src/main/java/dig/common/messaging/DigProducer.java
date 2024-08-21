package dig.common.messaging;

import dig.france.insee.sirene.messaging.event.SireneHistoricizedSearchEvent;
import dig.france.insee.sirene.search.response.SearchReportDto;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.rabbitmq.annotation.RabbitProperty;

@RabbitClient("${rabbitmq.exchange.dig}")
public interface DigProducer {

    @Binding("${rabbitmq.binding.insee.sirene.search-request}")
    void sendSireneHistoricizedSearchEvent(SireneHistoricizedSearchEvent event);

    @Binding("${rabbitmq.binding.insee.sirene.search-response}")
    void sendCompletedSireneSearchEvent(SearchReportDto reportDto);

    @Binding("${rabbitmq.binding.insee.sirene.ping}")
    @RabbitProperty(name = "contentType", value = "application/json")
    void sendPing(byte[] data);
}
