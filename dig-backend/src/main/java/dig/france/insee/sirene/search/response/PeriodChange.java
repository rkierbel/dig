package dig.france.insee.sirene.search.response;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
record PeriodChange(String reason,
                    String value) {

    static PeriodChange of(String reason, String value) {
        return new PeriodChange(reason, value);
    }

    public String toChangeString(String tab) {
        return "\n" + tab + "- change reason: %s ; value: %s".formatted(reason, value);
    }
}