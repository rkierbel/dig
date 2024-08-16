package dig.france.insee.sirene.search;

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

import java.util.Set;

@Singleton
@Slf4j
public class SireneSearchService {

    @Inject
    InseeHttpClient httpClient;

    @Inject
    SireneSearchMapper sireneSearchMapper;
    // TODO -> AOP logging

    SireneSearchResultDto sireneSearchByNaturalNameHistoricized(String term) {
        String queryString = SireneSearchFactory.historicized(Set.of(SearchCriteria.builder()
                .searchVar(SearchVariable.NATURAL_PERSON_NAME)
                .value(term)
                .operator(SearchOperator.NONE)
                .build()));
        log.info("Built query string for natural person name search: {}", queryString);
        return resultFromHttp(queryString, term);
    }

    void siretSearchBySiren(String siren) {
        String queryString = SireneSearchFactory.simpleSearch(SearchVariable.SIREN, siren);
        log.info("Built query string for natural person name search on the Siret register: {}", queryString);
        httpClient.siretSearch(queryString);
    }

    void siretSearchByNaturalNameHistoricized(String term) {
        String queryString = SireneSearchFactory.historicized(Set.of(SearchCriteria.builder()
                .searchVar(SearchVariable.NATURAL_PERSON_NAME)
                .value(term)
                .operator(SearchOperator.NONE)
                .build()));
        httpClient.siretSearch(queryString);
    }

    SireneSearchResultDto sireneSearchByMultiCriteriaHistoricized(Set<SearchCriteria> criteria) {
        String queryString = SireneSearchFactory.historicized(criteria);
        log.info("Built query string for multi-criteria search: {}", queryString);
        return resultFromHttp(queryString, SireneSearchFactory.logCriteria(criteria));
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
