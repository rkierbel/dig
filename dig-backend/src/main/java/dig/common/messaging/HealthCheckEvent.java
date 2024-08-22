package dig.common.messaging;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;

@Serdeable
@Builder(toBuilder = true)
public record HealthCheckEvent(String id,
                               String message) {

    @Override
    public String toString() {
        return "Id: %s; Message: %s".formatted(this.id, this.message);
    }
}
