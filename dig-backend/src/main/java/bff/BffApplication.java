package bff;

import bff.insee.InseeClientRunner;
import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.*;

@OpenAPIDefinition(
        info = @Info(
                title = "bff-application",
                version = "0.0"
        )
)
public class BffApplication {



    public static void main(String[] args) {
        Micronaut.run(InseeClientRunner.class, args);
    }
}