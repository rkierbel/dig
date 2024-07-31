package insee.sirene;

import insee.exception.SireneSearchException;
import insee.exception.EnumValueNotFoundException;
import util.EnumHelper;
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
        } catch (EnumValueNotFoundException evfex) {
            throw SireneSearchException.administrativeStatusNotFound(name);
        }
    }
}