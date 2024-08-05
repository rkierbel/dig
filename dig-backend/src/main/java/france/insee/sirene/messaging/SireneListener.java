package france.insee.sirene.messaging;

import common.messaging.event.SireneSearchEvent;
import france.insee.sirene.search.SireneSearchFactory;
import france.insee.httpclient.InseeHttpClient;
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
    InseeHttpClient httpClient;

    @Queue("${rabbitmq.queue.insee.sirene-search}")
    void onSireneSearchEvent(SireneSearchEvent sireneSearchEvent) {
        log.info("Received sirene search event with id {}", sireneSearchEvent.getId());
        Mono.from(httpClient.search(SireneSearchFactory.historicized(sireneSearchEvent.getSearchCriteria())))
                .subscribe(response -> log.info("Received sirene search response: {}", response));
    }
}
