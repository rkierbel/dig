package bff.insee;

import com.fasterxml.jackson.annotation.JsonValue;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@Introspected
enum SearchVariable {

    NATURAL_PERSON_NAME,
    COMPANY_NAME;

    @JsonValue
    public String operator() {
        return this.name();
    }
}
