package dig.france.insee.sirene.search;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import dig.france.insee.sirene.search.service.SireneSearchService;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Set;

@MicronautTest
@Disabled
//@WireMockTest(httpsEnabled = true)
public class SireneSearchTest {

    @Inject
    SireneController sireneController;

    @Inject
    SireneSearchService sireneSearchService;


    @Test
    void validSimpleSearchByNaturalPersonName_ShouldReturnSireneSearchResult() throws InterruptedException {
        var naturalPersonName = "grzeszezak";
        Thread.sleep(3000);
        sireneController.sireneSearchByName(naturalPersonName);
    }

    @Test
    void validSimpleSearchByMultipleSiren_shouldReturnSiretSearchResult() throws InterruptedException {
        var multiSiren = Set.of(923449979, 907971246, 391647690);
        Thread.sleep(3000);
        sireneSearchService.siretSearchByMultipleSiren(multiSiren);
    }
}
