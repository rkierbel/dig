package com.skilld;

import io.micronaut.context.BeanProvider;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.ClientFilter;
import io.micronaut.http.annotation.RequestFilter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@ClientFilter(serviceId = HttpServiceId.INSEE)
@Slf4j
class InseeHttpFilter {

    private final InseeConfig config;
    private final BeanProvider<InseeHttpClient> inseeHttpClient;
    private final int MAX_RETRY = 5;

    InseeHttpFilter(InseeConfig config, BeanProvider<InseeHttpClient> inseeHttpClient) {
        this.config = config;
        this.inseeHttpClient = inseeHttpClient;
    }

    //TODO -> configure number of retries + handle errors

    @RequestFilter("${insee.api.token}/**")
    public void doFilterInseeToken(MutableHttpRequest<?> request) {
        request.basicAuth(config.consumerKey(), config.consumerSecret());
    }

    @RequestFilter("${siren.api.prefix}/**")
    void doFilterSiren(MutableHttpRequest<?> request) {
        Mono.from(inseeHttpClient.get().token(InseeConfig.CLIENT_CREDENTIALS))
                .doOnError(InseeHttpException::logTokenGenerationFailure)
                .retry(MAX_RETRY)
                .blockOptional()
                .orElseThrow(InseeHttpException::invalidTokenResponse)
                .asyncToken()
                .subscribe(request::bearerAuth);
    }
}
