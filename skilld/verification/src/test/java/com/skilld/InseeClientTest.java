package com.skilld;

import io.micronaut.context.annotation.Property;
import io.micronaut.http.client.exceptions.HttpClientException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@MicronautTest(rebuildContext = true)
class InseeClientTest {

    @Inject
    InseeHttpClient client;

    @Test
    void givenValidCredentials_whenPostToken_validTokenGenerated() {
        InseeTokenResponse resp = Mono.from(client.token(InseeConfig.CLIENT_CREDENTIALS)).block();
        assertNotNull(resp);
        assertNotNull(resp.accessToken());
        assertEquals(InseeTokenResponse.BEARER, resp.tokenType());
    }

    @Test
    @Property(name = "insee.consumer-key", value = "a")
    void givenInvalidCredentials_whenPostToken_isBadRequest() {
        assertThrows(HttpClientException.class, () -> Mono.from(client.token(InseeConfig.CLIENT_CREDENTIALS)).block());
    }

    @Test
    void givenInvalidGrantType_whenPostToken_isBadRequest() {
        assertThrows(HttpClientException.class, () -> Mono.from(client.token("bad")).block());
    }
}