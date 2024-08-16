package dig.france.insee.sirene.search.result;

import com.fasterxml.jackson.annotation.JsonCreator;
import dig.common.exception.EnumValueNotFoundException;
import dig.common.util.EnumHelper;
import dig.france.insee.exception.SireneSearchException;

public enum UnitType {
    NATURAL_PERSON,
    LEGAL_ENTITY;

    @JsonCreator
    static UnitType fromName(String name) {
        try {
            return EnumHelper.helper().deSerEnum(name, UnitType.values());
        } catch (EnumValueNotFoundException notFoundEx) {
            throw SireneSearchException.unitTypeNotFound(name);
        }
    }
}
