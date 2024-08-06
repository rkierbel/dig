package france.insee.sirene;

import common.messaging.DigProducer;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Requires(notEnv = Environment.TEST)
@Slf4j
public class SireneService {

    @Inject
    DigProducer digProducer;

    /*public void sireneSearch(Set<SearchCriteria> searchCriteria) {
        SireneSimpleSearchEvent event = SireneSimpleSearchEvent.builder()
                .id(UUID.randomUUID().toString())
                .searchCriteria(searchCriteria)
                .build();
        log.info(event.criteria()); //TODO -> error here -> rework encapsulation
        digProducer.sendSireneSearchRequestEvent(event);
    }*/
}
