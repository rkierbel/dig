package com.nexus.messaging;

import com.nexus.insee.sirenesearch.SearchCriteria;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Set;

@AllArgsConstructor
@Builder(toBuilder = true)
@Serdeable
@Introspected
public class SireneSearchEvent {

    String id;
    Set<SearchCriteria> searchCriteria;
}
