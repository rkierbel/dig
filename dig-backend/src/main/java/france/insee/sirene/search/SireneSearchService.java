package france.insee.sirene.search;

import common.messaging.DigProducer;
import france.insee.InseeConstant;
import france.insee.exception.InseeHttpException;
import france.insee.httpclient.InseeHttpClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Set;

@Singleton
@Slf4j
public class SireneSearchService {

    @Inject
    InseeHttpClient httpClient;

    @Inject
    DigProducer digProducer;

    // TODO -> AOP logging


    public SireneSearchResponse historicizedNaturalPersonName(String term) {
        String queryString =  SireneSearchFactory.historicized(Set.of(SearchCriteria.builder()
                .searchVar(SearchVariable.NATURAL_PERSON_NAME)
                .value(term)
                .operator(SearchOperator.NONE)
                .build()));
        log.info("Built query string for natural person name search: {}", queryString);
        return Mono.from(httpClient.search(queryString))
                .doOnError(InseeHttpException::logSireneSearchFailure)
                .retry(InseeConstant.MAX_RETRY)
                .block(); // TODO handle complete empty (Mono returns null)
    }

    public void historicizedSearch(Set<SearchCriteria> searchCriteria) {
        Mono.from(httpClient.search(SireneSearchFactory.historicized(searchCriteria)))
                .doOnError(InseeHttpException::logSireneSearchFailure)
                .retry(InseeConstant.MAX_RETRY)
                .subscribe(digProducer::onSireneSearchResponse);
    }
}
