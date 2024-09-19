package dig.france.insee.sirene;

import dig.france.insee.sirene.search.request.SearchCriteria;
import dig.france.insee.sirene.search.response.SearchResponseDto;
import dig.france.insee.sirene.search.service.AsyncSireneSearchService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.stream.Collectors;

@Controller("/insee/sirene-async")
@Slf4j
public class AsyncSireneController {

    @Inject
    AsyncSireneSearchService searchService;

    @Get("/name")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<SearchResponseDto> sireneSearchByName(@QueryValue String term) {
        log.info("Sending HTTP request to Sirene for natural person or company with name {}", term);
        searchService.sireneSearchByCompanyOrNaturalName(term);
        return HttpResponse.ok();
    }

    @Get("/natural-name")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<SearchResponseDto> sireneSearchByNaturalName(@QueryValue String term) {
        log.info("Sending HTTP request to Sirene for natural person with name {}", term);
        searchService.sireneSearchByNaturalName(term);
        return HttpResponse.ok();
    }

    @Get("/company-name")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<SearchResponseDto> sireneSearchByCompanyName(@QueryValue String term) {
        log.info("Sending HTTP request to Sirene for company with name {}", term);
        searchService.sireneSearchByCompanyName(term);
        return HttpResponse.ok();
    }

    @Get("/multi-criteria")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<SearchResponseDto> sireneSearchByMultiCriteria(@QueryValue Set<SearchCriteria> searchCriteria) {
        log.info("Sending non-blocking request to Sirene for multi-criteria search {}",
                searchCriteria.stream().map(SearchCriteria::log).collect(Collectors.joining(", ")));
        searchService.sireneSearchByMultiCriteria(searchCriteria);
        return HttpResponse.ok();
    }

    @Get("/ping")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Void> ping(@QueryValue String message) {
        log.info("Received ping request with message: {}", message);
        searchService.healthCheck(message);
        return HttpResponse.ok();
    }
}
