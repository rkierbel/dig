package com.skilld.bff.insee;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@Introspected
public record SearchCriteria(@JsonProperty("search-var") SearchVariable searchVar,
                             @JsonProperty("value") String value,
                             @JsonProperty("search-op") SearchOperator operator) {

    static SearchCriteria from(SearchVariable searchVar,
                               String value,
                               SearchOperator operator) {
        return new SearchCriteria(searchVar, value, operator);
    }

    static SearchCriteria from(SearchVariable searchVar, String value) {
        return new SearchCriteria(searchVar, value, SearchOperator.NONE);
    }

    static SearchCriteria simpleSearch(String value) {
        return new SearchCriteria(SearchVariable.COMPANY_NAME, value, SearchOperator.NONE);
    }
}
