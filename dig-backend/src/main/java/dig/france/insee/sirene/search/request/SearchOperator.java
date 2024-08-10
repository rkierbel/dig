package dig.france.insee.sirene.search.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import dig.france.insee.exception.SireneSearchException;
import dig.common.exception.EnumValueNotFoundException;
import dig.common.util.EnumHelper;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;

@Serdeable
@Getter
@Introspected
public enum SearchOperator {
    AND,
    OR,
    NONE;

    @JsonCreator
    static SearchOperator fromName(String name) {
        try {
            return EnumHelper.helper().deSerEnum(name, SearchOperator.values());
        } catch (EnumValueNotFoundException notFoundEx) {
            throw SireneSearchException.searchOperatorNotFoundException(name);
        }
    }
}
