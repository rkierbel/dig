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
enum SearchVariable {

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
