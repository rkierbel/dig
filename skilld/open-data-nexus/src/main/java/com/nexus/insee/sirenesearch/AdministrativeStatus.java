package com.nexus.insee.sirenesearch;

import com.skilld.core.exception.EnumValueNotFoundException;
import com.skilld.core.exception.SireneSearchException;
import com.skilld.core.util.EnumHelper;
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