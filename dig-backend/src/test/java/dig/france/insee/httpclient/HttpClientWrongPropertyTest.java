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
    void givenInvalidCredentials_whenPostToken_isBadRequest() {
        StepVerifier.create(Mono.from(client.token(InseeConstant.CLIENT_CREDENTIALS)))
                .expectError(HttpClientException.class)
                .verifyThenAssertThat().hasNotDroppedErrors();
    }

    @Test
    void givenInvalidCredentials_whenGetInfo_isBadRequest() throws InterruptedException {
        StepVerifier.create(Flux.from(client.information()))
                .expectError(InseeHttpException.class)
                .verifyThenAssertThat()
                .hasNotDroppedErrors();
    }
}
