package france.insee.sirene.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import france.insee.exception.SireneSearchException;
import common.exception.EnumValueNotFoundException;
import common.util.EnumHelper;
import france.insee.sirene.SireneConstants;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;

@Serdeable
@Getter
@Introspected
public enum SearchVariable {

    NATURAL_PERSON_NAME(SireneConstants.NATURAL_PERSON_NAME),
    COMPANY_NAME(SireneConstants.COMPANY_NAME);

    final String fr;

    SearchVariable(String fr) {
        this.fr = fr;
    }

    public String fr() {
        return this.fr;
    }

    @JsonCreator
    static SearchVariable fromName(String name) {
        try {
            return EnumHelper.helper().deSerEnum(name, SearchVariable.values());
        } catch (EnumValueNotFoundException evfex) {
            throw SireneSearchException.searchVariableNotFoundException(name);
        }
    }
}
