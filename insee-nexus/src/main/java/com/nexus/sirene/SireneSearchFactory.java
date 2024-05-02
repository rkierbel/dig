package com.nexus.sirene;

import jakarta.inject.Singleton;

import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class SireneSearchFactory {

    static final String WHITESPACE = " ";

    String simple(String companyName) {
        return SearchCriteria.simpleSearch(companyName).toString();
    }

    public String historicized(Set<SearchCriteria> searchCriteria) {
        final String PERIOD = "periode(";
        final String CLOSING_BRACKET = ")";

        return PERIOD + toQueryString(searchCriteria) + CLOSING_BRACKET;
    }

    private String toQueryString(Set<SearchCriteria> searchCriteria) {
        return searchCriteria.stream()
                .map(SearchCriteria::toString)
                .collect(Collectors.joining(WHITESPACE));
    }
}
