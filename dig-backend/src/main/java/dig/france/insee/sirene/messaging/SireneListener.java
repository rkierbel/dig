package dig.france.insee.sirene.messaging;

import dig.france.insee.sirene.messaging.event.SireneHistoricizedSearchEvent;
import dig.france.insee.sirene.search.AsyncSireneSearchService;
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
    AsyncSireneSearchService asyncSireneSearchService;

    @Queue("${rabbitmq.queue.insee.sirene.search-request}")
    void onSireneHistoricizedSearchEvent(SireneHistoricizedSearchEvent event) {
        log.info("Received sirene historicizedSearch event with id {}", event.getId());
        asyncSireneSearchService.sireneSearchByMultiCriteriaHistoricized(event.getSearchCriteria());
    }

    @Queue("${rabbitmq.queue.insee.sirene.ping}")
    public void onPing(byte[] data) {
        log.info("[SireneListener] Received Sirene ping:\n {}", new String(data));
    }
}
