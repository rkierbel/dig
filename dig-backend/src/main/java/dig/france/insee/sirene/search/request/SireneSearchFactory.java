package dig.france.insee.sirene.search.request;

import dig.france.insee.InseeConstant;
import dig.france.insee.exception.SireneSearchException;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static dig.france.insee.InseeConstant.OR;
import static dig.france.insee.sirene.search.request.SearchOperator.NONE;

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
                .operator(NONE)
                .build();

        LinkedHashSet<SearchCriteria> set = new LinkedHashSet<>(2);
        set.addFirst(byNaturalPersonName);
        set.addLast(byCompanyName);

        return historicized(set);
    }

    public static String naturalPersonName(String term) {
        return historicized(Set.of(SearchCriteria.builder()
                .searchVar(SearchVariable.NATURAL_PERSON_NAME)
                .value(term)
                .operator(NONE)
                .build()));
    }

    public static String companyName(String term) {
        return historicized(Set.of(SearchCriteria.builder()
                .searchVar(SearchVariable.COMPANY_NAME)
                .value(term)
                .operator(NONE)
                .build()));
    }

    public static String historicized(Set<SearchCriteria> searchCriteria) {
        return searchCriteria.stream()
                .map(SireneSearchFactory::formatted)
                .collect(Collectors.joining(" "));
    }

    private static String formatted(SearchCriteria criteria) {
        final String PERIOD = "periode(";
        final String CLOSING_BRACKET = NONE.equals(criteria.operator()) ?
                ")" : ") %s".formatted(criteria.operator());

        return PERIOD +
                String.join(":", criteria.searchVar().frenchVariableName, criteria.value()) +
                CLOSING_BRACKET;
    }

    public static String multipleSiren(Set<Integer> sirenNumbers) {
        return Optional.ofNullable(sirenNumbers)
                .map(SearchCriteria::sirenSet)
                .map(criteria -> INSTANCE.toQueryString(criteria, OR))
                .orElseThrow(SireneSearchException::nullParameterForSiretSearch);
    }

    private String toQueryString(Set<SearchCriteria> searchCriteria, String delimiter) { // TODO -> test only one siren
        return searchCriteria.stream()
                .map(INSTANCE::stringCriteria)
                .collect(Collectors.joining(delimiter));
    }

    private String stringCriteria(SearchCriteria criteria) {
        String stringified = String.join(":", criteria.searchVar().getFrenchVariableName(), criteria.value());
        if (criteria.operator() != NONE) {
            stringified += InseeConstant.WHITESPACE + criteria.operator();
        }
        return stringified;
    }
}
