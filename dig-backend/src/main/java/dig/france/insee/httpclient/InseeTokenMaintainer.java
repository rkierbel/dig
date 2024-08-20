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

    public InseeTokenMaintainer(BeanProvider<InseeHttpClient> inseeHttpClient) {
        this.inseeHttpClient = inseeHttpClient;
    }

    public String bearer() {
        return this.tokenData.accessToken();
    }

    public boolean hasValidTokenData() {
        return  tokenNotNull() && !isExpired();
    }

    public boolean tokenNotNull() {
        return tokenData != null && tokenData.accessToken != null;
    }

    public boolean isExpired() {
        return tokenData.tokenCreation().isBefore(Instant.now().minus(6, ChronoUnit.DAYS));
    }

    @Scheduled(condition = "#{this.tokenNotNull() && this.isExpired()}")
    public void updateToken() {
        log.info("[TestTokenMaintainer::token] Begin update Sirene access token");
        if (hasValidTokenData()) {
            log.info("[TestTokenMaintainer::token] Sirene access token already valid");
            return;
        }
        fetchToken();
    }
    
    @EventListener
    @Requires(notEnv = Environment.TEST)
    void onStartUp(ServerStartupEvent event) {
        log.info("[InseeClientRunner::onStartup] Begin fetching Sirene access token");
        fetchToken();
    }

    private void fetchToken() {
        Mono.from(inseeHttpClient.get().token(InseeConstant.CLIENT_CREDENTIALS))
                .doOnError(InseeHttpException::logTokenGenerationFailure)
                .doOnNext(resp -> log.info("[InseeClientRunner::onStartup] Sirene access token successfully retrieved"))
                .retryWhen(Retry.fixedDelay(InseeConstant.MAX_RETRY, Duration.ofMillis(300L)))
                .subscribe(tokenResponse -> this.setTokenData(TokenData.of(tokenResponse.accessToken())));
    }

    //TODO -> add scheduled job every six days from startup to update token -> remember startup

    private record TokenData(String accessToken, Instant tokenCreation) {

        static TokenData of(String accessToken) {
            return new TokenData(accessToken, Instant.now());
        }
    }
}
