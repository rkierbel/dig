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

    public SireneSearchResponse naturalPersonSearch(String term) {
        SearchCriteria criteria = SearchCriteria.from(SearchVariable.NATURAL_PERSON_NAME, term);
        String query = SireneSearchFactory.historicized(Set.of(criteria));

        return Mono.from(this.httpClient.search(query)).block();
    }

    public void search(Set<SearchCriteria> searchCriteria) {
        Mono.from(httpClient.search(SireneSearchFactory.historicized(searchCriteria)))
                .doOnError(InseeHttpException::logSireneSearchFailure)
                .retry(InseeConstant.MAX_RETRY)
                .doOnSuccess(response -> log.info("Received sirene search response: {}", response)) //TODO->investigate diff with subscribe()
                .subscribe(digProducer::onSireneSearchResponse);
    }
}
