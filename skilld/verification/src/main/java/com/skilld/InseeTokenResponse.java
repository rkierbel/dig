package com.skilld;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
record InseeTokenResponse(@JsonProperty("access_token") String accessToken, @JsonProperty("token_type") String tokenType) {
    static final String BEARER = "Bearer";
}
