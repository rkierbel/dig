package dig.france.insee.sirene;

import dig.france.insee.sirene.search.SireneSearchService;
import dig.france.insee.sirene.search.request.SearchCriteria;
import dig.france.insee.sirene.search.request.SireneSearchFactory;
import dig.france.insee.sirene.search.result.SireneSearchResultDto;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

@Controller("/insee/sirene")
public class SireneController {

    private static final Logger log = LoggerFactory.getLogger(SireneController.class);
    @Inject SireneSearchService sireneSearchService;

    @Get("/natural-person")
    @Produces(MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.BLOCKING)
    public HttpResponse<SireneSearchResultDto> naturalPersonHistoricizedSearch(@QueryValue String term) {
        log.info("Sending HTTP request to Sirene for natural person with name {}", term);
        return HttpResponse.ok(sireneSearchService.historicizedNaturalPersonName(term));
    }

    @Get("/natural-person-async")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Void> naturalPersonHistoricizedSearchAsync(@QueryValue @ValidSireneSimpleSearch String term) {
        log.info("Sending non-blocking request to Sirene for natural person with name {}", term);
        sireneSearchService.historicizedNaturalPersonNameAsync(term);
        return HttpResponse.ok();
    }

    @Get("/multi-criteria")
    @Produces(MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.BLOCKING)
    public HttpResponse<SireneSearchResultDto> multiCriteriaSearch(@QueryValue Set<SearchCriteria> searchCriteria) {
        log.info("Sending HTTP request to Sirene for multi-criteria search {}", SireneSearchFactory.logCriteria(searchCriteria));
        return HttpResponse.ok(sireneSearchService.historicizedMultiCriteria(searchCriteria));
    }
}

