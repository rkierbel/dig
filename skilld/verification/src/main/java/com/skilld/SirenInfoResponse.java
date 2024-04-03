package com.skilld;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record SirenInfoResponse(@JsonProperty("versionService") String sirenVersion) {

}
