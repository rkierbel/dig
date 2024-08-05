package france.insee.sirene;

import common.messaging.DigProducer;
import common.messaging.event.SireneSearchEvent;
import france.insee.sirene.search.SireneSearchService;
import france.insee.sirene.validation.ValidSireneSearch;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import jakarta.inject.Inject;

@Controller("/insee/sirene")
public class SireneController {

    @Inject
    SireneSearchService sireneSearchService;

    @Inject
    DigProducer digProducer;

    @Get("/natural-person{?name}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Void> naturalPersonSearch(@Nullable @QueryValue @ValidSireneSearch String name) {
        // TODO -> validate request
        // TODO -> forwarding to business layer
        // TODO async -> perform business logic
        digProducer.sendSireneSearchRequestEvent(SireneSearchEvent.builder().build());
        return HttpResponse.ok();
    }
}
