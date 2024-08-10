package dig.common.messaging.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import dig.france.insee.sirene.search.request.SearchCriteria;
import dig.france.insee.sirene.search.request.SearchOperator;
import dig.france.insee.sirene.search.request.SearchVariable;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Builder(toBuilder = true)
@Serdeable
@Getter
@Introspected
public class SireneHistoricizedSearchEvent {

    private final @JsonProperty("sirene-search-event-id") String id = UUID.randomUUID().toString();
    private final @JsonProperty("sirene-search-criteria") Set<SearchCriteria> searchCriteria;

    @JsonCreator
    public SireneHistoricizedSearchEvent(Set<SearchCriteria> searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @JsonCreator
    public static SireneHistoricizedSearchEvent fromNaturalPersonName(String naturalPersonName) {
        return SireneHistoricizedSearchEvent.builder()
                .searchCriteria(Set.of(
                        SearchCriteria.builder()
                                .value(naturalPersonName)
                                .searchVar(SearchVariable.NATURAL_PERSON_NAME)
                                .operator(SearchOperator.NONE)
                                .build()))
                .build();
    }

    String criteria() {
        return new Gson().toJson(this);
    }
}
