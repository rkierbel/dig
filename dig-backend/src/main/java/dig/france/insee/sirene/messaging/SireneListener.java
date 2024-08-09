package dig.dig.france.insee.sirene.messaging;

import dig.common.messaging.event.SireneHistoricizedSearchEvent;
import dig.france.insee.sirene.search.SireneSearchService;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@RabbitListener
@Slf4j
@Requires(notEnv = Environment.TEST)
public class SireneListener {

    @Inject
    SireneSearchService searchService;

    @Queue("${rabbitmq.queue.insee.sirene.search-request}")
    void onSireneHistoricizedSearchEvent(SireneHistoricizedSearchEvent event) {
        log.info("Received sirene historicizedSearch event with id {}", event.getId());
        searchService.historicizedSearch(event.getSearchCriteria());
    }
}
