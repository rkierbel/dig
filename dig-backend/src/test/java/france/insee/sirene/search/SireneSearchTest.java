package france.insee.sirene.search;

import france.insee.httpclient.InseeHttpClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Set;

@MicronautTest
public class SireneSearchTest {

    @Inject
    InseeHttpClient client;

    @Inject
    SireneSearchFactory sireneSearch;

    @Test
    void givenValidSimpleSearch_returnSireneSearchResult() {
        var criteria = SearchCriteria.from(SearchVariable.NATURAL_PERSON_NAME, "grzeszezak");
        StepVerifier.create(Mono.from(client.search(sireneSearch.historicized(Set.of(criteria)))))
                .consumeNextWith(SireneSearchResponse::logSirenResponseInfo)
                .verifyComplete();
    }
}
