package dig.france.insee.sirene.search;

import dig.common.messaging.DigProducer;
import dig.france.insee.InseeConstant;
import dig.france.insee.exception.InseeHttpException;
import dig.france.insee.httpclient.InseeHttpClient;
import dig.france.insee.sirene.search.request.SearchCriteria;
import dig.france.insee.sirene.search.request.SearchOperator;
import dig.france.insee.sirene.search.request.SearchVariable;
import dig.france.insee.sirene.search.request.SireneSearchFactory;
import dig.france.insee.sirene.search.result.SireneSearchMapper;
import dig.france.insee.sirene.search.result.SireneSearchResponse;
import dig.france.insee.sirene.search.result.SireneSearchResultDto;
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

    @Inject
    SireneSearchMapper sireneSearchMapper;
    // TODO -> AOP logging

    public SireneSearchResultDto historicizedNaturalPersonName(String term) {
        String queryString = SireneSearchFactory.historicized(Set.of(SearchCriteria.builder()
                .searchVar(SearchVariable.NATURAL_PERSON_NAME)
                .value(term)
                .operator(SearchOperator.NONE)
                .build()));
        log.info("Built query string for natural person name search: {}", queryString);

        SireneSearchResultDto result = sireneSearchMapper.apiResponseToDto(httpClient.search(queryString));
        log.info("Mapping API search response to SearchResultDto: {}", result);

        return result; // TODO handle complete empty (Mono returns null)
    }

    public void historicizedSearch(Set<SearchCriteria> searchCriteria) {
        Mono.from(httpClient.searchAsync(SireneSearchFactory.historicized(searchCriteria)))
                .doOnError(InseeHttpException::logSireneSearchFailure)
                .retry(InseeConstant.MAX_RETRY)
                .subscribe(digProducer::onSireneSearchResponse);
    }
}
