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
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
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

    private Mono<SearchReportDto> completeSearchWithSiret(SireneSearchResponse apiResponse) {
        if (apiResponse.sireneUnits() == null || apiResponse.sireneUnits().isEmpty()) {
            return Mono.just(SearchReportDto.emptyReport());
        }
        return Flux.fromIterable(apiResponse.sireneUnits())
                .flatMap(this::completeSireneUnitDto)
                .collectList()
                .map(unitDtos -> SearchReportDto.builder().sireneUnits(unitDtos).build());
    }

    private Publisher<SearchReportDto.SireneUnitDto> completeSireneUnitDto(SireneSearchResponse.SireneUnit unit) {
        String query = SireneSearchFactory.simpleSearch(SearchVariable.SIREN, unit.sirenStr());
        return Mono.from(httpClient.siretSearchAsync(query)) // TODO -> can we do it in one request ? gather all the siren, then one query for all the siren ?
                .map(siretResponse -> sireneSearchMapper.toSireneUnitDto(unit, siretResponse))
                .onErrorResume(this::emptyMonoOnError);
    }

    private void handleSearchError(Throwable ex) {
        // TODO -> handle error
    }

    private Mono<SearchReportDto.SireneUnitDto> emptyMonoOnError(Throwable ex) {
        log.error("Error fetching Siret information: {}", ex.getMessage());
        return Mono.empty();
    }

}
