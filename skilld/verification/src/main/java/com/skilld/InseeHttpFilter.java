package com.skilld;

import io.micronaut.context.BeanProvider;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.ClientFilter;
import io.micronaut.http.annotation.RequestFilter;
import reactor.core.publisher.Mono;

@ClientFilter(serviceId = HttpServiceId.INSEE)
class InseeHttpFilter {

    private final InseeConfig config;
    private final BeanProvider<InseeHttpClient> inseeHttpClient;

    InseeHttpFilter(InseeConfig config, BeanProvider<InseeHttpClient> inseeHttpClient) {
        this.config = config;
        this.inseeHttpClient = inseeHttpClient;
    }

    //TODO -> configure number of retries + handle errors

    @RequestFilter("${insee.api.token}/**")
    public void doFilterToken(MutableHttpRequest<?> request) {
        request.basicAuth(config.consumerKey(), config.consumerSecret());
    }

    @RequestFilter("${siren.api.prefix}/**")
    void doFilterSiren(MutableHttpRequest<?> request) {
        Mono.from(inseeHttpClient.get().token(InseeConfig.CLIENT_CREDENTIALS))
                .block()
                .asyncToken()
                .subscribe(request::bearerAuth);
    }
}
