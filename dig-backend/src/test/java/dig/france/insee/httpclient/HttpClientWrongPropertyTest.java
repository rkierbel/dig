package dig.france.insee.httpclient;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import dig.france.insee.InseeConstant;
import dig.france.insee.exception.InseeHttpException;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.client.exceptions.HttpClientException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

@MicronautTest
@WireMockTest(httpsEnabled = true)
@Property(name="insee.consumer-key", value="WRONG_KEY")
public class HttpClientWrongPropertyTest {

    @Inject
    InseeHttpClient client;

    @Value("${insee.api.token}")
    private String tokenUrl;

    @Value("${wiremock.jsonpath.unauthorized-get-token}")
    private String accessTokenErrorResponse;

    @BeforeEach
    void setUp() {
        stubFor(get(tokenUrl)
                .willReturn(aResponse().withBodyFile(accessTokenErrorResponse).withStatus(401))
        );
    }

    @Test
    void invalidCredentials_whenGetToken_shouldBeBadRequest() {
        StepVerifier.create(Mono.from(client.token(InseeConstant.CLIENT_CREDENTIALS)))
                .expectError(HttpClientException.class)
                .verifyThenAssertThat()
                .hasNotDroppedErrors();
    }

    @Test
    void invalidCredentials_whenGetInfo_shouldBeBadRequest() throws InterruptedException {
        StepVerifier.create(Flux.from(client.information()))
                .expectError(InseeHttpException.class)
                .verifyThenAssertThat()
                .hasNotDroppedErrors();
    }
}
