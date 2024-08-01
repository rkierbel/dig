package france.insee.sirene.search;

import france.insee.httpclient.InseeHttpClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

import java.util.Set;

@Singleton
public class SireneSearchService {

    @Inject
    SireneSearchFactory searchFactory;

    @Inject
    InseeHttpClient httpClient;

    public SireneSearchResponse naturalPersonSearch(String term) {
        SearchCriteria criteria = SearchCriteria.from(SearchVariable.NATURAL_PERSON_NAME, term);
        String query = searchFactory.historicized(Set.of(criteria));

        return Mono.from(this.httpClient.search(query)).block();
    }
}
