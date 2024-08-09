package dig.france.insee.sirene;

import dig.france.insee.exception.SireneSearchException;
import dig.common.exception.EnumValueNotFoundException;
import dig.common.util.EnumHelper;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.micronaut.serde.annotation.Serdeable;

/**
 * A -> active
 * C -> ceased business activity
 */
@Serdeable
public enum AdministrativeStatus {
    A,
    C;

    @JsonCreator
    static AdministrativeStatus fromName(String name) {
        try {
            return EnumHelper.helper().deSerEnum(name, AdministrativeStatus.values());
        } catch (EnumValueNotFoundException notFoundEx) {
            throw SireneSearchException.administrativeStatusNotFound(name);
        }
    }
}