package dig.france.insee.sirene.search;

import dig.common.messaging.DigProducer;
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
                .retryWhen(Retry.fixedDelay(InseeConstant.MAX_RETRY, Duration.ofSeconds(1))) // TODO -> best practice ?
                .flatMap(this::completeSearchWithSiret)
                .subscribe(digProducer::sendCompletedSearchEvent, this::handleSearchError);
    }

    public void sireneSearchByMultiCriteriaHistoricized(Set<SearchCriteria> criteria) {
        Mono.from(httpClient.searchAsync(SireneSearchFactory.historicized(criteria)))
                .doOnError(InseeHttpException::logSireneSearchFailure)
                .retryWhen(Retry.fixedDelay(InseeConstant.MAX_RETRY, Duration.ofSeconds(1)))
                .flatMap(this::completeSearchWithSiret)
                .subscribe(digProducer::sendCompletedSearchEvent, this::handleSearchError);
    }

    //TODO -> multiple siren single request works => link establishments to siren in the report
    private Mono<SearchReportDto> completeSearchWithSiret(SireneSearchResponse apiResponse) {
        if (Objects.isNull(apiResponse.sirens())) {
            return Mono.just(SearchReportDto.emptyReport());
        }
        return Mono.from(httpClient.siretSearchAsync(SireneSearchFactory.multipleSiren(apiResponse.sirens())))
                .map(siretResponse -> sireneSearchMapper.toReportDto(apiResponse, siretResponse))
                .onErrorResume(this::emptyMonoOnError);
    }

    private void handleSearchError(Throwable ex) {
        // TODO -> handle error
    }

    private Mono<SearchReportDto> emptyMonoOnError(Throwable ex) {
        log.error("Error fetching Siret information: {}", ex.getMessage());
        return Mono.empty();
    }

}
