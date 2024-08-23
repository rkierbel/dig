package dig.france.insee.exception;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record InseeHttpError(String description,
                             String type) {
}
