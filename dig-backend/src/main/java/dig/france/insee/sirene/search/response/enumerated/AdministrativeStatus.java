package dig.france.insee.sirene.search.response.enumerated;

import dig.france.insee.exception.SireneSearchException;
import dig.common.exception.EnumValueNotFoundException;
import dig.common.util.EnumHelper;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.micronaut.serde.annotation.Serdeable;

/**
 * A -> active
 * C -> ceased business activity (Sirene unit level)
 * F -> "Fermé" -> concerns closed establishments
 */
@Serdeable
public enum AdministrativeStatus {
    A,
    C,
    F;

    @JsonCreator
    static AdministrativeStatus fromName(String name) {
        try {
            return EnumHelper.helper().deSerEnum(name, AdministrativeStatus.values());
        } catch (EnumValueNotFoundException notFoundEx) {
            throw SireneSearchException.administrativeStatusNotFound(name);
        }
    }
}