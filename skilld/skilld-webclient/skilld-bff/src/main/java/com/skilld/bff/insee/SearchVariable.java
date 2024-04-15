package com.skilld.bff.insee;

import com.fasterxml.jackson.annotation.JsonValue;
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

    @JsonValue
    public String operator() {
        return this.name();
    }
}
