package dig.france.insee.sirene.search;

import dig.common.messaging.DigProducer;
import dig.common.messaging.HealthCheckEvent;
import dig.france.insee.InseeConstant;
import dig.france.insee.exception.InseeHttpException;
import dig.france.insee.httpclient.InseeHttpClient;
import dig.france.insee.sirene.messaging.event.SireneSearchCompletedEvent;
import dig.france.insee.sirene.search.request.SearchCriteria;
import dig.france.insee.sirene.search.request.SearchOperator;
import dig.france.insee.sirene.search.request.SearchVariable;
import dig.france.insee.sirene.search.request.SireneSearchFactory;
import dig.france.insee.sirene.search.response.SireneSearchMapper;
import dig.france.insee.sirene.search.response.SireneSearchResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Singleton
@Slf4j
public class AsyncSireneSearchService {

    @Inject
    InseeHttpClient httpClient;

    @Inject
    DigProducer digProducer;

    @Inject
    SireneSearchMapper sireneSearchMapper;

    private static final long RETRY_DELAY = 500L;

    public void healthCheck(String message) {
        log.info("[AsyncSearchService::onPing] Forwarding ping with message {}, transforming to recordO", message);
        HealthCheckEvent event = new HealthCheckEvent(UUID.randomUUID().toString(), message);
        log.info("[AsyncSearchService::onPing] Sending healthcheck event with id {} and message {}", event.id(), event.message());
        digProducer.sendPing(event);
    }

    //TODO -> full test
    public void sireneSearchByNaturalNameHistoricized(String term) {
        executeSearchPipeline(
                Set.of(SearchCriteria.builder()
                        .searchVar(SearchVariable.NATURAL_PERSON_NAME)
                        .value(term)
                        .operator(SearchOperator.NONE)
                        .build())
        );
    }

    public void sireneSearchByMultiCriteriaHistoricized(Set<SearchCriteria> criteria) {
        executeSearchPipeline(criteria);
    }

    private void executeSearchPipeline(Set<SearchCriteria> criteria) {
        Mono.from(httpClient.searchAsync(SireneSearchFactory.historicized(criteria)))
                .doOnError(InseeHttpException::logSireneSearchFailure)
                .retryWhen(Retry.fixedDelay(InseeConstant.MAX_RETRY, Duration.ofMillis(RETRY_DELAY)))
                .flatMap(this::executeSiretSearch)
                .subscribe(digProducer::sendCompletedSireneSearchEvent, this::handleSearchError);
    }

    private Mono<SireneSearchCompletedEvent> executeSiretSearch(SireneSearchResponse apiResponse) {
        if (Objects.isNull(apiResponse.sirenNumbers())) {
            return Mono.just(SireneSearchCompletedEvent.emptyWithId());
        }
        return Mono.from(
                        httpClient.siretSearchAsync(
                                SireneSearchFactory.multipleSiren(apiResponse.sirenNumbers()))
                )
                .map(siretResponse -> sireneSearchMapper.toReport(apiResponse, siretResponse))
                .map(SireneSearchCompletedEvent::withReport)
                .onErrorResume(this::emptyMonoOnError);
    }

    private void handleSearchError(Throwable ex) {
        // TODO -> handle error
    }

    private Mono<SireneSearchCompletedEvent> emptyMonoOnError(Throwable ex) {
        log.error("Error fetching Siret information: {}", ex.getMessage());
        return Mono.just(SireneSearchCompletedEvent.emptyWithId());
    }
}
