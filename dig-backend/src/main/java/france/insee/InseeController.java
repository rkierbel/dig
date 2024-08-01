package france.insee;

import france.insee.sirene.validation.ValidSireneSearch;
import france.insee.sirene.search.SireneSearchResponse;
import france.insee.sirene.search.SireneSearchService;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import jakarta.inject.Inject;

@Controller("/insee")
public class InseeController { //TODO -> sirene + package

    @Inject
    SireneSearchService sireneSearchService;

    @Get("/sirene/natural-person{?name}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<SireneSearchResponse> naturalPersonSearch(@Nullable @QueryValue @ValidSireneSearch String name) {
        // TODO -> validate request
        // TODO -> ack validation + forwarding to business layer
        // TODO async -> perform business logic
/*        InseeValidator.validate(name);
        InseeService.search();*/
        return HttpResponse.ok(sireneSearchService.naturalPersonSearch(name));
    }
}
