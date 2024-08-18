package dig.france.insee.sirene.search.request;

import dig.france.insee.InseeConstant;
import dig.france.insee.exception.SireneSearchException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static dig.france.insee.InseeConstant.OR;
import static dig.france.insee.InseeConstant.WHITESPACE;


public class SireneSearchFactory {

    public static String simpleSearch(SearchVariable variable, String value) {
        return new SearchCriteria(variable, value, SearchOperator.NONE).toString();
    }

    public static String logCriteria(Set<SearchCriteria> searchCriteria) {
        return searchCriteria.stream().map(SearchCriteria::toString).collect(Collectors.joining(InseeConstant.WHITESPACE));
    }

    public static String historicized(Set<SearchCriteria> searchCriteria) {
        final String PERIOD = "periode(";
        final String CLOSING_BRACKET = ")";

        return PERIOD + toQueryString(searchCriteria) + CLOSING_BRACKET;
    }

    public static String multipleSiren(Set<Integer> sirenNumbers) {
        return Optional.ofNullable(sirenNumbers)
                .map(SearchCriteria::sirenSet)
                .map(criteria -> toQueryString(criteria, OR))
                .orElseThrow(SireneSearchException::nullParameterForSiretSearch);
    }

    private static String toQueryString(Set<SearchCriteria> searchCriteria) {
        return searchCriteria.stream()
                .map(SireneSearchFactory::stringCriteria)
                .collect(Collectors.joining(WHITESPACE));
    }

    private static String toQueryString(Set<SearchCriteria> searchCriteria, String delimiter) {
        return searchCriteria.stream()
                .map(SireneSearchFactory::stringCriteria)
                .collect(Collectors.joining(delimiter));
    }

    private static String stringCriteria(SearchCriteria criteria) {
        String stringified = String.join(":", criteria.searchVar().getFrenchVariableName(), criteria.value());

        return SearchOperator.NONE.equals(criteria.operator()) ?
                stringified :
                stringified + InseeConstant.WHITESPACE + criteria.operator();
    }
}
