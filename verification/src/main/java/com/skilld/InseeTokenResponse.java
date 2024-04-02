package com.skilld;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record InseeTokenResponse(String accessToken, String tokenType) {
}
