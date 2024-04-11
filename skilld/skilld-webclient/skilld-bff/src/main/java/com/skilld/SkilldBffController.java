package com.skilld;

import io.micronaut.http.annotation.*;

@Controller("/skilld-bff")
public class SkilldBffController {

    @Get(uri = "/", produces = "text/plain")
    public String index() {
        return "Example Response";
    }
}