package dig;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.*;

@OpenAPIDefinition(
        info = @Info(
                title = "dig-application",
                version = "0.0"
        )
)
public class DigApplication {

    //TODO -> CLI
    public static void main(String[] args) {
        Micronaut.run(DigApplication.class, args);
    }
}