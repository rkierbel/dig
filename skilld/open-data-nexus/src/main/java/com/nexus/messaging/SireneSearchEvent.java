package com.nexus.messaging;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nexus.insee.sirenesearch.SearchCriteria;
import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder(toBuilder = true)
@Serdeable
@Getter
@Introspected
public class SireneSearchEvent {

    private final @JsonProperty("search-event-id") String id;
    private final @JsonProperty("search-criteria") Set<SearchCriteria> searchCriteria;

    @JsonCreator
    public SireneSearchEvent(@JsonProperty("search-event-id") String id,
                             @JsonProperty("search-criteria") Set<SearchCriteria> searchCriteria) {
        this.id = id;
        this.searchCriteria = searchCriteria;
    }
}
