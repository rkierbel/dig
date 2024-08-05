package common.messaging.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import france.insee.sirene.search.SearchCriteria;
import france.insee.sirene.search.SearchOperator;
import france.insee.sirene.search.SearchVariable;
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
public class SireneSearchEvent {

    private final @JsonProperty("search-event-id") String id = UUID.randomUUID().toString();
    private final @JsonProperty("search-criteria") Set<SearchCriteria> searchCriteria;

    @JsonCreator
    public SireneSearchEvent(Set<SearchCriteria> searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @JsonCreator
    public static SireneSearchEvent naturalPersonSearch(String naturalPersonName) {
        return SireneSearchEvent.builder().searchCriteria(
                Set.of(SearchCriteria.builder()
                        .searchVar(SearchVariable.NATURAL_PERSON_NAME)
                        .value(naturalPersonName)
                        .operator(SearchOperator.NONE)
                        .build())
                ).build();
    }

    String criteria() {
        return new Gson().toJson(this);
    }
}
