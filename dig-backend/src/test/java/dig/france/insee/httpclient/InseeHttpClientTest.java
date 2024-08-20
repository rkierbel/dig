package dig.france.insee.httpclient;

import dig.france.insee.InseeConstant;
import dig.france.insee.sirene.SirenInfoResponse;
import io.micronaut.http.client.exceptions.HttpClientException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
class InseeHttpClientTest {

    @Inject
    InseeHttpClient client;

    @Inject
    InseeTokenMaintainer tokenMaintainer;

    @Test
    void givenValidCredentials_whenPostToken_validTokenGenerated() {
        InseeTokenResponse resp = Mono.from(client.token(InseeConstant.CLIENT_CREDENTIALS)).block();
        assertNotNull(resp);
        assertNotNull(resp.accessToken());
        assertEquals(InseeTokenResponse.BEARER, resp.tokenType());
    }

    @Test
    void givenInvalidGrantType_whenPostToken_isBadRequest() {
        assertThrows(HttpClientException.class, () -> Mono.from(client.token("bad")).block());
    }

    @Test
    void givenValidBearerToken_whenGetInfoFromSirenV3_isOk() throws InterruptedException {
        tokenMaintainer.updateToken();
        Thread.sleep(1000);
        SirenInfoResponse infoResponse = Mono.from(client.information()).block();
        assertNotNull(infoResponse);
        assertNotNull(infoResponse.sirenVersion());
        assertTrue(infoResponse.sirenVersion().contains("3"));
    }
}