package dig.france.insee.sirene.search.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;

import java.util.Set;
import java.util.stream.Collectors;

@Serdeable
@Builder(toBuilder = true)
public record SearchCriteria(@JsonProperty("historicizedSearch-var") SearchVariable searchVar,
                             @JsonProperty("value") String value,
                             @JsonProperty("historicizedSearch-op") SearchOperator operator) {

    private SearchCriteria(SearchVariable searchVar, String value) {
        this(searchVar, value, SearchOperator.NONE);
    }

    static Set<SearchCriteria> sirenSet(Set<Integer> sirenNumbers) {
        return sirenNumbers.stream()
                .map(SearchCriteria::siren)
                .collect(Collectors.toSet());
    }

    private static SearchCriteria siren(Integer integer) {
        return new SearchCriteria(SearchVariable.SIREN, String.valueOf(integer));
    }

    public String log() {
        return "%s : %s".formatted(searchVar.getFrenchVariableName(), value);
    }
}
