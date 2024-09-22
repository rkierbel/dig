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

    public static String companyOrNaturalName(String term) {
        SearchCriteria byNaturalPersonName = SearchCriteria.builder()
                .searchVar(SearchVariable.NATURAL_PERSON_NAME)
                .value(term)
                .operator(SearchOperator.OR)
                .build();
        SearchCriteria byCompanyName = SearchCriteria.builder()
                .searchVar(SearchVariable.COMPANY_NAME)
                .value(term)
                .operator(SearchOperator.NONE)
                .build();
        return historicized(Set.of(byNaturalPersonName, byCompanyName));
    }

    public static String naturalPersonName(String term) {
        return historicized(Set.of(SearchCriteria.builder()
                .searchVar(SearchVariable.NATURAL_PERSON_NAME)
                .value(term)
                .operator(SearchOperator.NONE)
                .build()));
    }

    public static String companyName(String term) {
        return historicized(Set.of(SearchCriteria.builder()
                .searchVar(SearchVariable.COMPANY_NAME)
                .value(term)
                .operator(SearchOperator.NONE)
                .build()));
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
