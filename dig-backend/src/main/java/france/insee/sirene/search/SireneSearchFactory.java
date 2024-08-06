package france.insee.sirene.search;

import france.insee.InseeConstant;

import java.util.Set;
import java.util.stream.Collectors;


public class SireneSearchFactory {


    public static String simpleSearch(String value) {
        return new SearchCriteria(SearchVariable.COMPANY_NAME, value, SearchOperator.NONE).toString();
    }


    public static String historicized(Set<SearchCriteria> searchCriteria) {
        final String PERIOD = "periode(";
        final String CLOSING_BRACKET = ")";

        return PERIOD + toQueryString(searchCriteria) + CLOSING_BRACKET;
    }

    private static String toQueryString(Set<SearchCriteria> searchCriteria) {
        return searchCriteria.stream()
                .map(SearchCriteria::toString)
                .collect(Collectors.joining(InseeConstant.WHITESPACE));
    }
}
