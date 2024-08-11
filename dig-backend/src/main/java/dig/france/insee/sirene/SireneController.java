package dig.france.insee.sirene;

import dig.common.messaging.DigProducer;
import dig.common.messaging.event.SireneHistoricizedSearchEvent;
import dig.france.insee.sirene.search.SireneSearchService;
import dig.france.insee.sirene.search.result.SireneSearchResultDto;
import dig.france.insee.sirene.validation.ValidSireneSimpleSearch;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/insee/sirene")
public class SireneController {

    private static final Logger log = LoggerFactory.getLogger(SireneController.class);
    @Inject
    SireneSearchService sireneSearchService;

    @Inject
    DigProducer digProducer;


    @Get("/natural-person")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<SireneSearchResultDto> naturalPersonHistoricizedSearch(@Nullable @QueryValue String term) {
        log.info("Sending HTTP request to Sirene for natural person with name {}", term);

        return HttpResponse.ok(sireneSearchService.historicizedNaturalPersonName(term));
    }

    @Get("/natural-person-async")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Void> naturalPersonHistoricizedSearchAsync(@Nullable @QueryValue @ValidSireneSimpleSearch String term) {
        // TODO -> validate request
        // TODO -> forwarding to business layer
        // TODO async -> perform business logic
        digProducer.sendSireneHistoricizedSearchEvent(SireneHistoricizedSearchEvent.fromNaturalPersonName(term));
        return HttpResponse.ok();
    }
}
