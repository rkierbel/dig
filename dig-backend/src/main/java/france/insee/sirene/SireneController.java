package france.insee.sirene;

import common.messaging.DigProducer;
import common.messaging.event.SireneHistoricizedSearchEvent;
import france.insee.sirene.search.SireneSearchResponse;
import france.insee.sirene.search.SireneSearchService;
import france.insee.sirene.validation.ValidSireneSimpleSearch;
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

    @Get("/natural-person{?term}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<SireneSearchResponse> naturalPersonHistoricizedSearch(@Nullable @QueryValue @ValidSireneSimpleSearch String term) {
        return HttpResponse.ok(sireneSearchService.historicizedNaturalPersonName(term));
    }

    @Get("/natural-person-async{?term}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Void> naturalPersonHistoricizedSearchAsync(@Nullable @QueryValue @ValidSireneSimpleSearch String term) {

        // TODO -> validate request
        // TODO -> forwarding to business layer
        // TODO async -> perform business logic
        digProducer.sendSireneHistoricizedSearchEvent(SireneHistoricizedSearchEvent.fromNaturalPersonName(term));
        return HttpResponse.ok();
    }
}
