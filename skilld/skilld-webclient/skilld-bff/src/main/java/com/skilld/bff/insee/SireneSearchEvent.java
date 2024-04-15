package com.skilld.bff.insee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder(toBuilder = true)
@Serdeable
@Slf4j
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

    String criteria() {
        return new Gson().toJson(this);
    }
}
