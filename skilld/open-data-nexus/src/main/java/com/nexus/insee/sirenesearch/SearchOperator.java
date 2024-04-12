package com.nexus.insee.sirenesearch;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.skilld.core.exception.EnumValueNotFoundException;
import com.skilld.core.exception.SireneSearchException;
import com.skilld.core.util.EnumHelper;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public enum SearchOperator {
    AND,
    OR,
    NONE;

    @JsonCreator
    static SearchOperator fromName(String name) {
        try {
            return EnumHelper.helper().deSerEnum(name, SearchOperator.values());
        } catch (EnumValueNotFoundException evfex) {
            throw SireneSearchException.searchOperatorNotFoundException(name);
        }
    }
}
