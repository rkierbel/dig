package dig.france.insee.sirene.search;

import dig.common.messaging.DigProducer;
import dig.common.messaging.HealthCheckEvent;
import dig.france.insee.InseeConstant;
import dig.france.insee.exception.InseeHttpException;
import dig.france.insee.httpclient.InseeHttpClient;
import dig.france.insee.sirene.search.request.SearchCriteria;
import dig.france.insee.sirene.search.request.SearchOperator;
import dig.france.insee.sirene.search.request.SearchVariable;
import dig.france.insee.sirene.search.request.SireneSearchFactory;
import dig.france.insee.sirene.search.response.SearchReportDto;
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

    //TODO -> full test
    public void sireneSearchByNaturalNameHistoricized(String term) {
        Mono.from(httpClient.searchAsync(SireneSearchFactory.historicized(Set.of(SearchCriteria.builder()
                        .searchVar(SearchVariable.NATURAL_PERSON_NAME)
                        .value(term)
                        .operator(SearchOperator.NONE)
                        .build()))))
                .doOnError(InseeHttpException::logSireneSearchFailure)
                .retryWhen(Retry.fixedDelay(InseeConstant.MAX_RETRY, Duration.ofMillis(500L))) // TODO -> best practice ?
                .flatMap(this::completeSearchWithSiret)
                .subscribe(digProducer::sendCompletedSireneSearchEvent, this::handleSearchError);
    }

    public void sireneSearchByMultiCriteriaHistoricized(Set<SearchCriteria> criteria) {
        Mono.from(httpClient.searchAsync(SireneSearchFactory.historicized(criteria)))
                .doOnError(InseeHttpException::logSireneSearchFailure)
                .retryWhen(Retry.fixedDelay(InseeConstant.MAX_RETRY, Duration.ofMillis(500L)))
                .flatMap(this::completeSearchWithSiret)
                .subscribe(digProducer::sendCompletedSireneSearchEvent, this::handleSearchError);
    }

    //TODO -> multiple siren single request works => link establishments to siren in the report
    private Mono<SearchReportDto> completeSearchWithSiret(SireneSearchResponse apiResponse) {
        if (Objects.isNull(apiResponse.sirenNumbers())) {
            return Mono.just(SearchReportDto.emptyReport());
        }
        return Mono.from(httpClient.siretSearchAsync(SireneSearchFactory.multipleSiren(apiResponse.sirenNumbers())))
                .map(siretResponse -> sireneSearchMapper.toReport(apiResponse, siretResponse))
                .onErrorResume(this::emptyMonoOnError);
    }

    private void handleSearchError(Throwable ex) {
        // TODO -> handle error
    }

    private Mono<SearchReportDto> emptyMonoOnError(Throwable ex) {
        log.error("Error fetching Siret information: {}", ex.getMessage());
        return Mono.empty();
    }

    public void ping(String message) {
        log.info("[AsyncSearchService::onPing] Forwarding ping with message {}", message);
        HealthCheckEvent event  = new HealthCheckEvent(UUID.randomUUID().toString(), message);
        log.info("[AsyncSearchService::onPing] Sending healthcheck event with id {} and message {}", event.getId(), event.getMessage());
        digProducer.sendPing(message.getBytes());
    }
}
