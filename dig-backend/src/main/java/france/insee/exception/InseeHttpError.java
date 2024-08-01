package france.insee.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record InseeHttpError(@JsonProperty("error_description") String description,
                             @JsonProperty("error") String type) {
}
