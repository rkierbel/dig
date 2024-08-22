package dig.france.insee.sirene.messaging;

import dig.common.messaging.HealthCheckEvent;
import dig.france.insee.sirene.messaging.event.SireneSearchCompletedEvent;
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

    @Queue("${rabbitmq.queue.insee.sirene.search-response}")
    void onSireneSearchCompletedEvent(SireneSearchCompletedEvent event) {
        log.info("Received sirene search completed event with id {} and report:\n{}", event.id(), event.reportDto().toString());
    }

    @Queue("${rabbitmq.queue.insee.sirene.ping}")
    public void onPing(HealthCheckEvent event) {
        log.info("[SireneListener] Received Sirene ping:\n {}", event.toString());
    }
}
