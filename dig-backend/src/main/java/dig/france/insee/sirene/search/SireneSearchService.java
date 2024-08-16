package dig.france.insee.sirene.search;

import dig.common.messaging.DigProducer;
import dig.france.insee.InseeConstant;
import dig.france.insee.exception.InseeHttpException;
import dig.france.insee.exception.SireneSearchException;
import dig.france.insee.httpclient.InseeHttpClient;
import dig.france.insee.sirene.search.request.SearchCriteria;
import dig.france.insee.sirene.search.request.SearchOperator;
import dig.france.insee.sirene.search.request.SearchVariable;
import dig.france.insee.sirene.search.request.SireneSearchFactory;
import dig.france.insee.sirene.search.result.SireneSearchMapper;
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
        return resultFromHttp(queryString, term);

    }

    public SireneSearchResultDto historicizedMultiCriteria(Set<SearchCriteria> criteria) {
        String queryString = SireneSearchFactory.historicized(criteria);
        log.info("Built query string for multi-criteria search: {}", queryString);
        return resultFromHttp(queryString, SireneSearchFactory.logCriteria(criteria));
    }

    public void historicizedNaturalPersonNameAsync(String term) {
        Mono.from(httpClient.searchAsync(SireneSearchFactory.historicized(Set.of(SearchCriteria.builder()
                        .searchVar(SearchVariable.NATURAL_PERSON_NAME)
                        .value(term)
                        .operator(SearchOperator.NONE)
                        .build()))))
                .doOnError(InseeHttpException::logSireneSearchFailure)
                .retry(InseeConstant.MAX_RETRY)
                .subscribe(digProducer::onSireneSearchResponse);
    }

    public void historicizedMultiCriteriaAsync(Set<SearchCriteria> criteria) {
        Mono.from(httpClient.searchAsync(SireneSearchFactory.historicized(criteria)))
                .doOnError(InseeHttpException::logSireneSearchFailure)
                .retry(InseeConstant.MAX_RETRY)
                .subscribe(digProducer::onSireneSearchResponse);
    }

    private SireneSearchResultDto resultFromHttp(String query, String exceptionMessage) {
        try {
            SireneSearchResultDto result = sireneSearchMapper.apiResponseToDto(httpClient.search(query));
            log.info("Mapping API search response to SearchResultDto: {}", result);
            return result;
        } catch (SireneSearchException searchException) {
            throw SireneSearchException.historicizedSearchFailure(exceptionMessage);
        }
    }
}
