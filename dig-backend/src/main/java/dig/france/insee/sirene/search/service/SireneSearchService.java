package dig.france.insee.sirene.search.service;

import dig.france.insee.httpclient.InseeHttpClient;
import dig.france.insee.sirene.search.request.SearchCriteria;
import dig.france.insee.sirene.search.request.SireneSearchFactory;
import dig.france.insee.sirene.search.response.SearchResponseDto;
import dig.france.insee.sirene.search.response.SireneResponseMapper;
import dig.france.insee.sirene.search.response.SireneSearchResponse;
import dig.france.insee.sirene.search.response.SiretSearchResponse;
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
    SireneResponseMapper sireneResponseMapper;

    public SearchResponseDto sireneSearchByCompanyOrNaturalName(String term) {
        String queryString = SireneSearchFactory.companyOrNaturalName(term);
        log.info("Built query string for natural person OR company name search: {}", queryString);
        return fullResultFromHttp(queryString);
    }

    public SearchResponseDto sireneSearchByNaturalName(String term) {
        String queryString = SireneSearchFactory.naturalPersonName(term);
        log.info("Built query string for natural person name search: {}", queryString);
        return fullResultFromHttp(queryString);
    }

    public SearchResponseDto sireneSearchByCompanyName(String term) {
        String queryString = SireneSearchFactory.companyName(term);
        log.info("Built query string for company name search: {}", queryString);
        return fullResultFromHttp(queryString);
    }

    public void siretSearchByMultipleSiren(Set<Integer> sirenNumbers) {
        String queryString = SireneSearchFactory.multipleSiren(sirenNumbers);
        log.info("Built query string for multiple siren search on the Siret register: {}", queryString);
        httpClient.siretSearch(queryString);
    }

    public SearchResponseDto sireneSearchByMultiCriteria(Set<SearchCriteria> criteria) {
        String queryString = SireneSearchFactory.historicized(criteria);
        log.info("Built query string for multi-criteria search: {}", queryString);
        return fullResultFromHttp(queryString);
    }

    private SearchResponseDto fullResultFromHttp(String query) {
        SireneSearchResponse sireneResponse = httpClient.sireneSearch(query);
        log.info("Successfully retrieved Sirene data");

        SiretSearchResponse siretResponse = httpClient.siretSearch(
                SireneSearchFactory.multipleSiren(sireneResponse.sirenNumbers()));
        log.info("Successfully retrieved Siret register data");

        SearchResponseDto report = sireneResponseMapper.toDto(sireneResponse, siretResponse);

        if (log.isDebugEnabled()) {
            log.debug("Successfully generated a search report ! {}", report);
        }
        return report;
    }
}
