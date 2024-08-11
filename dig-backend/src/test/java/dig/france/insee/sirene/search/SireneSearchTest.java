package dig.france.insee.sirene.search;

import dig.france.insee.sirene.SireneController;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
public class SireneSearchTest {

    @Inject
    SireneController sireneController;

    @Test
    void givenValidSimpleSearch_returnSireneSearchResult() throws InterruptedException {
        var naturalPersonName = "grzeszezak";
        Thread.sleep(1000);
        sireneController.naturalPersonHistoricizedSearch(naturalPersonName);
    }
}
