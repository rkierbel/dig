package dig.france.insee.sirene.search.response.enumerated;

import com.fasterxml.jackson.annotation.JsonCreator;
import dig.common.exception.EnumValueNotFoundException;
import dig.france.insee.exception.SireneSearchException;
import io.micronaut.serde.annotation.Serdeable;

/**
 * A -> active
 * C -> ceased business activity (Sirene unit level)
 * F -> "Fermé" -> concerns closed establishments
 */
@Serdeable
public enum AdministrativeStatus {
    A("active"),
    C("ceased"),
    F("closed");

    private final String meaning;

    AdministrativeStatus(String meaning) {
        this.meaning = meaning;
    }

    public String meaning() {
        return this.meaning;
    }

    @JsonCreator
    static String fromName(String name) {
        try {
            return Enum.valueOf(AdministrativeStatus.class, name).meaning();
        } catch (EnumValueNotFoundException notFoundEx) {
            throw SireneSearchException.administrativeStatusNotFound(name);
        }
    }
}