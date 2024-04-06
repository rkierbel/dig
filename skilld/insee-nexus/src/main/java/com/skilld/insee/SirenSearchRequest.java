package com.skilld.insee;

import java.util.Set;
import java.util.stream.Collectors;

public class SirenSearchRequest {

    static final String WHITESPACE = " ";

    String toSimpleSearch(String companyName) {
        return Criteria.simpleSearch(companyName).toString();
    }

    // TODO -> What will we receive as input for the search ?
    String toMultiVariableSearch(Set<Criteria> searchCriteria) {
        final String PERIOD = "periode(";
        final String CLOSING_BRACKET = ")";

        return PERIOD +
                searchCriteria.stream()
                        .map(Criteria::toString)
                        .collect(Collectors.joining(WHITESPACE)) +
                CLOSING_BRACKET;
    }

    private record Criteria(SearchVariable key, String value, SearchOperator operator) {

        static Criteria from(SearchVariable key, String value, SearchOperator operator) {
            return new Criteria(key, value, operator);
        }

        static Criteria from(SearchVariable key, String value) {
            return new Criteria(key, value, SearchOperator.NONE);
        }

        static Criteria simpleSearch(String value) {
            return new Criteria(SearchVariable.COMPANY_NAME, value, SearchOperator.NONE);
        }

        @Override
        public String toString() {
            String stringified = String.join(":", key().name(), value());

            return SearchOperator.NONE.equals(this.operator()) ?
                    stringified :
                    stringified + WHITESPACE + operator();
        }
    }

    enum SearchVariable {
        BUSINESS_UNIT_NAME("nomUniteLegale"),
        COMPANY_NAME("raisonSociale");

        SearchVariable(String inseeName) {
        }
    }

    enum SearchOperator {
        AND,
        OR,
        NONE
    }
}
