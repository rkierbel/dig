package insee;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.*;

@OpenAPIDefinition(
        info = @Info(
                title = "nexus-application",
                version = "0.0"
        )
)
public class NexusApplication {

    //TODO -> CLI + event based comm from BFF
    public static void main(String[] args) {
        Micronaut.run(NexusApplication.class, args);
    }
}