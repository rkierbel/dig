package bff;

import io.micronaut.http.annotation.*;

@Controller("/dig-bff")
public class DigBffController {

    @Get(uri = "/", produces = "text/plain")
    public String index() {
        return "Example Response";
    }
}