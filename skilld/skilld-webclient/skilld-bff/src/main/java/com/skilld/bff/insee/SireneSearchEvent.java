package com.skilld.bff.insee;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Builder(toBuilder = true)
@Serdeable
public class SireneSearchEvent {

    String id;
    Set<SearchCriteria> searchCriteria;
}
