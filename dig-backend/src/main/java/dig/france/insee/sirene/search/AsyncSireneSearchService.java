package dig.france.insee.sirene.search;

import dig.common.messaging.DigProducer;
import dig.france.insee.InseeConstant;
import dig.france.insee.exception.InseeHttpException;
import dig.france.insee.httpclient.InseeHttpClient;
import dig.france.insee.sirene.search.request.SearchCriteria;
import dig.france.insee.sirene.search.request.SearchOperator;
import dig.france.insee.sirene.search.request.SearchVariable;
import dig.france.insee.sirene.search.request.SireneSearchFactory;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

import java.util.Set;

@Singleton
public class AsyncSireneSearchService {

    @Inject
    InseeHttpClient httpClient;

    @Inject
    DigProducer digProducer;

    void sireneSearchByNaturalNameHistoricized(String term) {
        Mono.from(httpClient.searchAsync(SireneSearchFactory.historicized(Set.of(SearchCriteria.builder()
                        .searchVar(SearchVariable.NATURAL_PERSON_NAME)
                        .value(term)
                        .operator(SearchOperator.NONE)
                        .build()))))
                .doOnError(InseeHttpException::logSireneSearchFailure)
                .retry(InseeConstant.MAX_RETRY)
                .subscribe(digProducer::onSireneSearchResponse);
    }

    public void sireneSearchByMultiCriteriaHistoricized(Set<SearchCriteria> criteria) {
        Mono.from(httpClient.searchAsync(SireneSearchFactory.historicized(criteria)))
                .doOnError(InseeHttpException::logSireneSearchFailure)
                .retry(InseeConstant.MAX_RETRY)
                .subscribe(digProducer::onSireneSearchResponse);
    }
}
