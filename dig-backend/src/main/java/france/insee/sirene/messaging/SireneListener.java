package france.insee.sirene.messaging;

import common.messaging.event.SireneSearchEvent;
import france.insee.sirene.search.SireneSearchFactory;
import france.insee.httpclient.InseeHttpClient;
import france.insee.sirene.search.SireneSearchService;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RabbitListener
@Slf4j
@Requires(notEnv = Environment.TEST)
public class SireneListener {

    @Inject
    SireneSearchService searchService;

    @Queue("${rabbitmq.queue.insee.sirene.search-request}")
    void onSireneSearchEvent(SireneSearchEvent sireneSearchEvent) {
        log.info("Received sirene search event with id {}", sireneSearchEvent.getId());
        searchService.search(sireneSearchEvent.getSearchCriteria());
    }
}
