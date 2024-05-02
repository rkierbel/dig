package com.skilld.bff.insee;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Singleton
@Slf4j
@Requires(notEnv = Environment.TEST)
public class InseeClientRunner {

    @Inject
    SireneService sireneService;

    @EventListener
    public void onStartUp(ServerStartupEvent event) {
        log.info("[skilld-bff.InseeClientRunner::onStartUp] Starting sirene search async flow!");
        sireneService.sireneSearch(
                Set.of(SearchCriteria.from(SearchVariable.NATURAL_PERSON_NAME, "grzeszezak"))
        );
    }
}
