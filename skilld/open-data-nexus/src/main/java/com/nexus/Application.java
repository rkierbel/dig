package com.nexus;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.*;

@OpenAPIDefinition(
        info = @Info(
                title = "open-data-nexus",
                version = "0.0"
        )
)
public class Application {

    //TODO -> CLI + event based comm from BFF
    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}