package dig.france.insee.httpclient;

import dig.france.insee.InseeConstant;
import dig.france.insee.exception.InseeHttpException;
import dig.common.http.HttpServiceId;
import io.micronaut.context.BeanProvider;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.ClientFilter;
import io.micronaut.http.annotation.RequestFilter;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@ClientFilter(serviceId = HttpServiceId.INSEE)
@Slf4j
class InseeHttpFilter {

    private final InseeHttpConfig config;
    private final BeanProvider<InseeHttpClient> inseeHttpClient;
    @Setter private TokenData tokenData;

    InseeHttpFilter(InseeHttpConfig config,
                    BeanProvider<InseeHttpClient> inseeHttpClient) {
        this.config = config;
        this.inseeHttpClient = inseeHttpClient;
    }

    @RequestFilter("${insee.api.token}${micronaut.http.wildcard}")
    void doFilterInseeToken(MutableHttpRequest<?> request) {
        request.basicAuth(config.consumerKey(), config.consumerSecret());
    }

    @RequestFilter("${sirene.api.prefix}${micronaut.http.wildcard}")
    void doFilterSirene(MutableHttpRequest<?> request) {
        if (noValidTokenDate() || tokenData.isExpired()) {
            throw InseeHttpException.missingBearerToken();
        } else {
            request.bearerAuth(tokenData.accessToken);
        }
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

    private boolean noValidTokenDate() {
        return tokenData == null || tokenData.accessToken == null;
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
