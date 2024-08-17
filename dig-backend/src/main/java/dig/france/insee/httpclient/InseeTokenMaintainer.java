package dig.france.insee.httpclient;

import dig.france.insee.InseeConstant;
import dig.france.insee.exception.InseeHttpException;
import io.micronaut.context.BeanProvider;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Singleton;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Singleton
@Slf4j
class InseeTokenMaintainer {

    @Setter(AccessLevel.PRIVATE)
    private TokenData tokenData;

    private final BeanProvider<InseeHttpClient> inseeHttpClient;

    public InseeTokenMaintainer(BeanProvider<InseeHttpClient> inseeHttpClient) {
        this.inseeHttpClient = inseeHttpClient;
    }

    public String bearer() {
        return this.tokenData.accessToken();
    }

    public boolean hasValidTokenData() {
        return tokenData != null && tokenData.accessToken != null && !tokenData.isExpired();
    }

    @EventListener
    void onStartUp(ServerStartupEvent event) {
        log.info("[InseeClientRunner::onStartUp] Begin authentication");
        Mono.from(inseeHttpClient.get().token(InseeConstant.CLIENT_CREDENTIALS))
                .doOnError(InseeHttpException::logTokenGenerationFailure)
                .doOnNext(resp -> log.info("[InseeClientRunner::onStartUp] Successfully retrieved token"))
                .retry(InseeConstant.MAX_RETRY)
                .subscribe(tokenResponse -> this.setTokenData(TokenData.of(tokenResponse.accessToken())));
    }

    private record TokenData(String accessToken, Instant tokenCreation) {

        static TokenData of(String accessToken) {
            return new TokenData(accessToken, Instant.now());
        }

        boolean isExpired() {
            return tokenCreation.isBefore(Instant.now().minus(6, ChronoUnit.DAYS));
        }
    }
}
