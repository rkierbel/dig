package com.skilld;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
record InseeTokenResponse(String accessToken, String tokenType) {
}
