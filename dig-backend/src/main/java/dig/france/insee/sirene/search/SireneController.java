package dig.france.insee.sirene.search;

import dig.france.insee.InseeConstant;
import dig.france.insee.sirene.search.request.SearchCriteria;
import dig.france.insee.sirene.search.response.SearchResponseDto;
import dig.france.insee.sirene.search.service.SireneSearchService;
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
@ExecuteOn(TaskExecutors.BLOCKING)
public class SireneController {

    @Inject
    SireneSearchService searchService;

    @Get("/name")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<SearchResponseDto> sireneSearchByName(@QueryValue String term) {
        log.info("Sending HTTP request to Sirene for natural person or company with name {}", term);
        return HttpResponse.ok(searchService.sireneSearchByCompanyOrNaturalName(term));
    }

    @Get("/natural-name")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<SearchResponseDto> sireneSearchByNaturalName(@QueryValue String term) {
        log.info("Sending HTTP request to Sirene for natural person with name {}", term);
        return HttpResponse.ok(searchService.sireneSearchByNaturalName(term));
    }

    @Get("/company-name")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<SearchResponseDto> sireneSearchByCompanyName(@QueryValue String term) {
        log.info("Sending HTTP request to Sirene for company with name {}", term);
        return HttpResponse.ok(searchService.sireneSearchByCompanyName(term));
    }

    @Get("/multi-criteria")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<SearchResponseDto> sireneSearchByMultiCriteria(@QueryValue Set<SearchCriteria> searchCriteria) {
        log.info("Sending HTTP request to Sirene for multi-criteria search {}",
                searchCriteria.stream().map(SearchCriteria::toString).collect(Collectors.joining(InseeConstant.WHITESPACE)));
        return HttpResponse.ok(searchService.sireneSearchByMultiCriteria(searchCriteria));
    }
}

