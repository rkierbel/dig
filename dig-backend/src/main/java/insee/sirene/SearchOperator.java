package insee.sirene;

import com.fasterxml.jackson.annotation.JsonCreator;
import insee.exception.SireneSearchException;
import insee.exception.EnumValueNotFoundException;
import util.EnumHelper;
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
        } catch (EnumValueNotFoundException evfex) {
            throw SireneSearchException.searchOperatorNotFoundException(name);
        }
    }
}
