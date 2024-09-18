package dig.france.insee.sirene.search.response.enumerated;

import com.fasterxml.jackson.annotation.JsonCreator;
import dig.common.exception.EnumValueNotFoundException;
import dig.france.insee.exception.SireneSearchException;

public enum UnitType {
    NATURAL_PERSON,
    LEGAL_ENTITY;

    public String value() {
        return this.name().toLowerCase().replace("_", " ");
    }

    @JsonCreator
    static String fromName(String name) {
        try {
            return name.toLowerCase().replace("_", " ");
        } catch (EnumValueNotFoundException notFoundEx) {
            throw SireneSearchException.unitTypeNotFound(name);
        }
    }
}
