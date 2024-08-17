package dig.france.insee.sirene.search;

import dig.common.messaging.DigProducer;
import dig.france.insee.InseeConstant;
import dig.france.insee.exception.InseeHttpException;
import dig.france.insee.httpclient.InseeHttpClient;
import dig.france.insee.sirene.search.request.SearchCriteria;
import dig.france.insee.sirene.search.request.SearchOperator;
import dig.france.insee.sirene.search.request.SearchVariable;
import dig.france.insee.sirene.search.request.SireneSearchFactory;
import dig.france.insee.sirene.search.response.SireneSearchResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Singleton
public class AsyncSireneSearchService {

    @Inject
    InseeHttpClient httpClient;

    @Inject
    DigProducer digProducer;

    //TODO -> full test + weigh interest of fire and forget VS subscribe to result alternatives
    public Mono<SireneSearchResponse> sireneSearchByNaturalNameHistoricized(String term) {
        return Mono.from(httpClient.searchAsync(SireneSearchFactory.historicized(Set.of(SearchCriteria.builder()
                        .searchVar(SearchVariable.NATURAL_PERSON_NAME)
                        .value(term)
                        .operator(SearchOperator.NONE)
                        .build()))))
                .doOnError(InseeHttpException::logSireneSearchFailure)
                .retry(InseeConstant.MAX_RETRY)
                .flatMap(this::completeSearchWithSiret)
                .doOnNext(digProducer::sendCompletedSearchEvent);
    }

    public Mono<SireneSearchResponse> sireneSearchByMultiCriteriaHistoricized(Set<SearchCriteria> criteria) {
        return Mono.from(httpClient.searchAsync(SireneSearchFactory.historicized(criteria)))
                .doOnError(InseeHttpException::logSireneSearchFailure)
                .retry(InseeConstant.MAX_RETRY)
                .flatMap(this::completeSearchWithSiret)
                .doOnNext(digProducer::sendCompletedSearchEvent);
    }

    private Mono<SireneSearchResponse> completeSearchWithSiret(SireneSearchResponse apiResponse) {
        if (apiResponse.sirens() != null) {
            return Flux.fromIterable(apiResponse.sireneUnits())
                    .flatMap(unit -> {
                        String query = SireneSearchFactory.simpleSearch(SearchVariable.SIREN, unit.sirenStr());
                        return Mono.from(httpClient.siretSearchAsync(query)).doOnNext(unit::setEstablishments);
                    })
                    .then(Mono.just(apiResponse));
        }
        return Mono.just(apiResponse);
    }
}
