package com.nexus.messaging;

import com.google.gson.Gson;
import com.nexus.insee.httpclient.InseeHttpClient;
import com.nexus.insee.sirenesearch.SireneSearchFactory;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.core.annotation.Introspected;
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

    @Inject
    SireneSearchFactory searchFactory;

    @Queue("${rabbitmq.queue.insee.sirene-search}")
    void onSireneSearchEvent(SireneSearchEvent sireneSearchEvent) {
        log.info("[open-data-nexus.Insee.HttpClient::onSireneSearchEvent] Received sirene search event!");
        log.info(new Gson().toJson(sireneSearchEvent));
        Mono.from(httpClient.search(searchFactory.historicized(sireneSearchEvent.getSearchCriteria())))
                .subscribe(response -> log.info("Received sirene search response ! {}", response));

    }
}
