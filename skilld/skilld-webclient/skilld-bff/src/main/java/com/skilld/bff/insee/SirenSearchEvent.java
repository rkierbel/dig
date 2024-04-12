package com.skilld.bff.insee;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@AllArgsConstructor
@Builder(toBuilder = true)
public class SirenSearchEvent {

    String id;
    List<SearchCriteria> searchCriteria;
}
