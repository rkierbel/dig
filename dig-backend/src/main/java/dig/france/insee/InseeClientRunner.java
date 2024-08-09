package dig.france.insee;

import dig.france.insee.sirene.SireneService;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
@Requires(notEnv = Environment.TEST)
public class InseeClientRunner {

    @Inject
    SireneService sireneService;

    // TODO -> rework encapsulation of SearchCriteria
/*    @EventListener
    public void onStartUp(ServerStartupEvent event) {
        log.info("[dig-bff.InseeClientRunner::onStartUp] Starting sirene historicizedSearch async flow!");
        sireneService.sireneSearch(
                Set.of(SearchCriteria.from(SearchVariable.NATURAL_PERSON_NAME, "grzeszezak"))
        );
    }*/
}
