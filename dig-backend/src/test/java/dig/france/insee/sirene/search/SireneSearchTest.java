package dig.france.insee.sirene.search;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Set;

@MicronautTest
public class SireneSearchTest {

    @Inject
    SireneController sireneController;

    @Inject
    SireneSearchService sireneSearchService;

    @Test
    void givenValidSimpleSearchByNaturalPersonName_returnSireneSearchResult() throws InterruptedException {
        var naturalPersonName = "grzeszezak";
        Thread.sleep(3000);
        sireneController.sireneSearchByNaturalNameHistoricized(naturalPersonName);
    }

    @Test
    void givenValidSimpleSearchByMultipleSiren_returnSiretSearchResult() throws InterruptedException {
        var multiSiren = Set.of(923449979, 907971246, 391647690);
        Thread.sleep(3000);
        sireneSearchService.siretSearchByMultipleSiren(multiSiren);
    }
}
