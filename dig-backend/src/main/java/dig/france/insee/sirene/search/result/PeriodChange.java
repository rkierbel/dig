package dig.france.insee.sirene.search.result;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
record PeriodChange(String reason,
                    String value) {

    static PeriodChange of(String reason, String value) {
        return new PeriodChange(reason, value);
    }

    @Override
    public String toString() {
        return "\n\t\t- change reason: %s ; value: %s".formatted(reason, value);
    }
}