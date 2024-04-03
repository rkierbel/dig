package com.skilld;

import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.ClientFilter;
import io.micronaut.http.annotation.RequestFilter;

@ClientFilter(serviceId = HttpServiceId.INSEE, value = "/token/**")
class InseeHttpFilter {

    private final InseeConfig config;

    InseeHttpFilter(InseeConfig config) {
        this.config = config;
    }

    @RequestFilter
    public void doFilter(MutableHttpRequest<?> request) {
        request.basicAuth(config.consumerKey(), config.consumerSecret());
    }
}
