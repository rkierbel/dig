package france.insee.sirene.search;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
public class SireneSearchTest {

    @Inject
    SireneSearchService searchService;


    @Test
    void givenValidSimpleSearch_returnSireneSearchResult() {
        var naturalPersonName =  "grzeszezak";

        searchService.historicizedNaturalPersonName(naturalPersonName);
    }
}
