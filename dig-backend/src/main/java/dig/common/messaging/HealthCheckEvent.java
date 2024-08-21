package dig.common.messaging;

import io.micronaut.core.annotation.Creator;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;

@Serdeable
@Getter
@Setter
public class HealthCheckEvent {

    private final String id;
    private final String message;

    @Creator
    public HealthCheckEvent(String id, String message) {
        this.id = id;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Id: %s; Message: %s".formatted(id, message);
    }
}
