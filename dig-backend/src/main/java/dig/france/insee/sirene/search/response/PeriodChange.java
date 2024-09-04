package dig.france.insee.sirene.search.response;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
record PeriodChange(Reason reason,
                    String value) {

    static PeriodChange of(Reason reason, String value) {
        return new PeriodChange(reason, value);
    }

    public String toChangeString(String tab) {
        return "\n" + tab + "- change reason: %s ; value: %s".formatted(reason, value);
    }

    enum Reason {
        ADMIN_STATUS_CHANGE,
        NATURAL_PERSON_NAME_CHANGE,
        NATURAL_PERSON_COMMON_NAME_CHANGE,
        COMPANY_NAME_CHANGE,
        COMPANY_COMMON_NAME_CHANGE,
        LEGAL_CATEGORY_CHANGE,
        MAIN_ACTIVITY_CHANGE,
        ESTABLISHMENT_ADMIN_STATUS_CHANGE,
        ESTABLISHMENT_NAME_CHANGE,
        ESTABLISHMENT_COMMON_NAME_CHANGE,
        ESTABLISHMENT_MAIN_ACTIVITY_CHANGE,
        EMPLOYER_TYPE_CHANGE;
    }
}