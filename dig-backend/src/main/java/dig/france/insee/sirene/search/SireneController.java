package dig.france.insee.sirene.search;

import dig.france.insee.sirene.search.request.SearchCriteria;
import dig.france.insee.sirene.search.request.SireneSearchFactory;
import dig.france.insee.sirene.search.response.SearchReportDto;
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
    public HttpResponse<SearchReportDto> sireneSearchByNaturalNameHistoricized(@QueryValue String term) {
        log.info("Sending HTTP request to Sirene for natural person with name {}", term); //TODO _> adapt search -> denom OR surnames / firstnames etc
        return HttpResponse.ok(searchService.sireneSearchByNaturalNameHistoricized(term));
    }

    @Get("/multi-criteria")
    @Produces(MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.BLOCKING)
    public HttpResponse<SearchReportDto> sireneSearchByMultiCriteriaHistoricized(@QueryValue Set<SearchCriteria> searchCriteria) {
        log.info("Sending HTTP request to Sirene for multi-criteria search {}", SireneSearchFactory.logCriteria(searchCriteria));
        return HttpResponse.ok(searchService.sireneSearchByMultiCriteriaHistoricized(searchCriteria));
    }

    @Get("/natural-person-async")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Void> asyncSireneSearchByNaturalNameHistoricized(@QueryValue @ValidSireneSimpleSearch String term) {
        log.info("Sending non-blocking request to Sirene for natural person with name {}", term);
        asyncSearchService.sireneSearchByNaturalNameHistoricized(term);
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

