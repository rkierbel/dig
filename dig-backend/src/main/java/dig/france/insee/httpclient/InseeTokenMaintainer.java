package dig.france.insee.httpclient;

import dig.france.insee.InseeConstant;
import dig.france.insee.exception.InseeHttpException;
import io.micronaut.context.BeanProvider;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Singleton
@Slf4j
class InseeTokenMaintainer {

    @Setter(AccessLevel.PRIVATE)
    private TokenData tokenData;

    private final BeanProvider<InseeHttpClient> inseeHttpClient;

    InseeTokenMaintainer(BeanProvider<InseeHttpClient> inseeHttpClient) {
        this.inseeHttpClient = inseeHttpClient;
    }

    String bearer() {
        return this.tokenData.accessToken();
    }

    boolean hasValidTokenData() {
        return tokenNotNull() && !isExpired();
    }

    boolean tokenNotNull() {
        return tokenData != null && tokenData.accessToken != null;
    }

    boolean isExpired() {
        return tokenData.tokenCreation.isBefore(Instant.now().minus(6, ChronoUnit.DAYS));
    }

    @Scheduled(fixedDelay = "P6DT1H", initialDelay = "PT10m")
    void updateToken() {
        log.info("[TestTokenMaintainer::updateTokenJob] Begin update Sirene access token");
        if (hasValidTokenData()) {
            log.info("[TestTokenMaintainer::updateTokenJob] Sirene access token already valid");
        } else {
            log.info("[TestTokenMaintainer::updateTokenJob] Sirene access token is invalid, fetching token");
            executeTokenFetchPipeline()
                    .subscribe(tokenResponse -> this.setTokenData(TokenData.of(tokenResponse.accessToken())));
        }
    }

    @EventListener
    @Requires(notEnv = Environment.TEST)
    void onStartUp(ServerStartupEvent event) {
        log.info("[InseeClientRunner::onStartup] Begin fetching Sirene access token");
        executeTokenFetchPipeline()
                .subscribe(tokenResponse -> this.setTokenData(TokenData.of(tokenResponse.accessToken())));
    }

    private Mono<InseeTokenResponse> executeTokenFetchPipeline() {
        return Mono.from(inseeHttpClient.get().token(InseeConstant.CLIENT_CREDENTIALS))
                .doOnError(InseeHttpException::logTokenGenerationFailure)
                .doOnNext(response -> log.info("[InseeClientRunner::onStartup] Sirene access token successfully retrieved"))
                .retryWhen(Retry.fixedDelay(InseeConstant.MAX_RETRY, Duration.ofMillis(300L)));
    }

    private record TokenData(String accessToken, Instant tokenCreation) {

        static TokenData of(String accessToken) {
            return new TokenData(accessToken, Instant.now());
        }
    }
}
