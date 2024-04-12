package com.skilld.bff.insee;

import com.skilld.bff.messaging.SireneProducer;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Set;
import java.util.UUID;

@Singleton
@Requires(notEnv = Environment.TEST)
public class InseeService {

    @Inject
    SireneProducer sireneProducer;

    void sireneSearch(Set<SearchCriteria> searchCriteria) {
        sireneProducer.sendSireneSearchEvent(
                SireneSearchEvent.builder()
                        .id(UUID.randomUUID().toString())
                        .searchCriteria(searchCriteria)
                        .build()
        );
    }
}
