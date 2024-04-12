package com.skilld.bff.insee;

import lombok.Getter;

@Getter
enum SearchVariable {
    BUSINESS_UNIT_NAME("nomUniteLegale"),
    COMPANY_NAME("raisonSociale");

    final String searchVariable;

    SearchVariable(String searchVariable) {
        this.searchVariable = searchVariable;
    }
}
