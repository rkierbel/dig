package com.nexus;

import com.nexus.sirene.SireneSearchResponse;
import com.nexus.sirene.SireneSearchService;
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
    public HttpResponse<SireneSearchResponse> naturalPersonSearch(@QueryValue String name) {
        return HttpResponse.ok(sireneSearchService.naturalPersonSearch(name));
    }
}
