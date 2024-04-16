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
enum SearchVariable {

    BUSINESS_UNIT_NAME("nomUniteLegale"),
    COMPANY_NAME("raisonSociale");

    final String searchVariable;

    SearchVariable(String searchVariable) {
        this.searchVariable = searchVariable;
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
