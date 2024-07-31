package bff.insee;

import com.fasterxml.jackson.annotation.JsonValue;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@Introspected
enum SearchOperator {
    AND,
    OR,
    NONE;

    @JsonValue
    public String operator() {
        return this.name();
    }
}
