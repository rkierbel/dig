package dig.france.insee.sirene.search;

import dig.france.insee.InseeConstant;
import dig.france.insee.sirene.search.request.SearchCriteria;
import dig.france.insee.sirene.search.response.SearchReportDto;
import dig.france.insee.sirene.search.service.AsyncSireneSearchService;
import dig.france.insee.sirene.search.service.SireneSearchService;
import dig.france.insee.sirene.validation.ValidSireneSimpleSearch;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.stream.Collectors;

@Controller("/insee/sirene")
@Slf4j
public class SireneController {

    @Inject
    SireneSearchService searchService;

    @Inject
    AsyncSireneSearchService asyncSearchService;

    @Get("/natural-person")
    @Produces(MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.BLOCKING)
    public HttpResponse<SearchReportDto> sireneSearchByNaturalName(@QueryValue String term) {
        log.info("Sending HTTP request to Sirene for natural person with name {}", term);
        return HttpResponse.ok(searchService.sireneSearchByNaturalName(term));
    }

    @Get("/multi-criteria")
    @Produces(MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.BLOCKING)
    public HttpResponse<SearchReportDto> sireneSearchByMultiCriteria(@QueryValue Set<SearchCriteria> searchCriteria) {
        log.info("Sending HTTP request to Sirene for multi-criteria search {}",
                searchCriteria.stream().map(SearchCriteria::toString).collect(Collectors.joining(InseeConstant.WHITESPACE)));
        return HttpResponse.ok(searchService.sireneSearchByMultiCriteria(searchCriteria));
    }

    @Get("/natural-person-async")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Void> asyncSireneSearchByNaturalName(@QueryValue @ValidSireneSimpleSearch String term) {
        log.info("Sending non-blocking request to Sirene for natural person with name {}", term);
        asyncSearchService.sireneSearchByNaturalName(term);
        return HttpResponse.ok();
    }

    @Get("/multi-criteria")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<SearchReportDto> asyncSireneSearchByMultiCriteria(@QueryValue Set<SearchCriteria> searchCriteria) {
        log.info("Sending non-blocking request to Sirene for multi-criteria search {}",
                searchCriteria.stream().map(SearchCriteria::log).collect(Collectors.joining(", ")));
        asyncSearchService.sireneSearchByMultiCriteria(searchCriteria);
        return HttpResponse.ok();
    }

    @Get("/ping")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Void> ping(@QueryValue String message) {
        log.info("Received ping request with message: {}", message);
        asyncSearchService.healthCheck(message);
        return HttpResponse.ok();
    }
}

