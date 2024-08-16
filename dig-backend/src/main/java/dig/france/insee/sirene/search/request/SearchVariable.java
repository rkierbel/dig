package dig.france.insee.sirene.search.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import dig.france.insee.exception.SireneSearchException;
import dig.common.exception.EnumValueNotFoundException;
import dig.common.util.EnumHelper;
import dig.france.insee.sirene.SireneConstants;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;

@Serdeable
@Getter
@Introspected
public enum SearchVariable {

    NATURAL_PERSON_NAME(SireneConstants.NATURAL_PERSON_NAME),
    COMPANY_NAME(SireneConstants.COMPANY_NAME);

    final String frenchVariableName;

    SearchVariable(String frenchVariableName) {
        this.frenchVariableName = frenchVariableName;
    }

    public String frenchVariableName() {
        return this.frenchVariableName;
    }

    @JsonCreator
    static SearchVariable fromName(String name) {
        try {
            return EnumHelper.helper().deSerEnum(name, SearchVariable.values());
        } catch (EnumValueNotFoundException notFoundEx) {
            throw SireneSearchException.searchVariableNotFound(name);
        }
    }
}
