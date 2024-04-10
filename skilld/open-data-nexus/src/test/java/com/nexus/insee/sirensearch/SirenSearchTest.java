package com.nexus.insee.sirensearch;

import com.nexus.insee.httpclient.InseeHttpClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Set;

@MicronautTest
public class SirenSearchTest {

    @Inject
    InseeHttpClient client;

    @Inject
    SirenSearchFactory sirenSearch;

    @Test
    void givenValidSimpleSearch_returnSirenSearchResult() {
        var criteria = SearchCriteria.from(SearchVariable.BUSINESS_UNIT_NAME, "grzeszezak");
        StepVerifier.create(Mono.from(
                        client.search(
                                sirenSearch.historicized(Set.of(criteria))))
                )
                .consumeNextWith(SirenSearchResponse::logSirenResponseInfo)
                .verifyComplete();
    }
}
