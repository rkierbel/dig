package com.nexus.insee.sirenesearch;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.nexus.insee.exception.SireneSearchException;
import com.skilld.core.exception.EnumValueNotFoundException;
import com.skilld.core.util.EnumHelper;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;

@Serdeable
@Getter
@Introspected
public enum SearchOperator {
    AND,
    OR,
    NONE;

    @JsonValue
    public String operator() {
        return this.name();
    }

    @JsonCreator
    static SearchOperator fromName(String name) {
        try {
            return EnumHelper.helper().deSerEnum(name, SearchOperator.values());
        } catch (EnumValueNotFoundException evfex) {
            throw SireneSearchException.searchOperatorNotFoundException(name);
        }
    }
}
