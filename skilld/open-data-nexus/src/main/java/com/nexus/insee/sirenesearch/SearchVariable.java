package com.nexus.insee.sirenesearch;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;

@Getter
@Serdeable
enum SearchVariable {
    BUSINESS_UNIT_NAME("nomUniteLegale"),
    COMPANY_NAME("raisonSociale");

    final String searchVariable;

    SearchVariable(String searchVariable) {
        this.searchVariable = searchVariable;
    }
}
