package dig.france.insee.httpclient;

import dig.france.insee.InseeConstant;
import dig.france.insee.exception.InseeHttpException;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.client.exceptions.HttpClientException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@MicronautTest
@Property(name = "insee.consumer-key", value = "bad")
public class HttpClientWrongPropertyTest {

    @Inject
    InseeHttpClient client;

    @Test
    void invalidCredentials_whenGetToken_shouldBeBadRequest() {
        StepVerifier.create(Mono.from(client.token(InseeConstant.CLIENT_CREDENTIALS)))
                .expectError(HttpClientException.class)
                .verifyThenAssertThat().hasNotDroppedErrors();
    }

    @Test
    void invalidCredentials_whenGetInfo_shouldBeBadRequest() throws InterruptedException {
        StepVerifier.create(Flux.from(client.information()))
                .expectError(InseeHttpException.class)
                .verifyThenAssertThat()
                .hasNotDroppedErrors();
    }
}
