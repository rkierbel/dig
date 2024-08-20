package dig.france.insee.sirene.search.request;

import dig.france.insee.InseeConstant;
import dig.france.insee.exception.SireneSearchException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static dig.france.insee.InseeConstant.OR;
import static dig.france.insee.InseeConstant.WHITESPACE;

public class SireneSearchFactory {

    private static final SireneSearchFactory INSTANCE = new SireneSearchFactory();

    public static String simpleSearch(SearchVariable variable, String value) {
        return INSTANCE.stringCriteria(SearchCriteria.builder()
                .searchVar(variable)
                .value(value)
                .operator(SearchOperator.NONE)
                .build());
    }

    public static String historicized(Set<SearchCriteria> searchCriteria) {
        final String PERIOD = "periode(";
        final String CLOSING_BRACKET = ")";

        return PERIOD + INSTANCE.toQueryString(searchCriteria) + CLOSING_BRACKET;
    }

    public static String multipleSiren(Set<Integer> sirenNumbers) {
        return Optional.ofNullable(sirenNumbers)
                .map(SearchCriteria::sirenSet)
                .map(criteria -> INSTANCE.toQueryString(criteria, OR))
                .orElseThrow(SireneSearchException::nullParameterForSiretSearch);
    }

    public static String logCriteria(Set<SearchCriteria> searchCriteria) {
        return searchCriteria.stream().map(SearchCriteria::toString).collect(Collectors.joining(InseeConstant.WHITESPACE));
    }

    private String toQueryString(Set<SearchCriteria> searchCriteria) {
        return searchCriteria.stream()
                .map(INSTANCE::stringCriteria)
                .collect(Collectors.joining(WHITESPACE));
    }

    private String toQueryString(Set<SearchCriteria> searchCriteria, String delimiter) { // TODO -> test only one siren
        return searchCriteria.stream()
                .map(INSTANCE::stringCriteria)
                .collect(Collectors.joining(delimiter));
    }

    private String stringCriteria(SearchCriteria criteria) {
        String stringified = String.join(":", criteria.searchVar().getFrenchVariableName(), criteria.value());
        if (criteria.operator() != SearchOperator.NONE) {
            stringified += InseeConstant.WHITESPACE + criteria.operator();
        }
        return stringified;
    }
}
