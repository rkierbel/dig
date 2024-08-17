package dig.france.insee.httpclient;

import dig.france.insee.exception.InseeHttpException;
import dig.common.http.HttpServiceId;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.ClientFilter;
import io.micronaut.http.annotation.RequestFilter;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@ClientFilter(serviceId = HttpServiceId.INSEE)
@Slf4j
class InseeHttpFilter {

    @Inject
    private InseeHttpConfig config;

    @Inject
    private InseeTokenMaintainer tokenMaintainer;

    @RequestFilter("${insee.api.token}${micronaut.http.wildcard}")
    void doFilterInseeToken(MutableHttpRequest<?> request) {
        request.basicAuth(config.consumerKey(), config.consumerSecret());
    }

    @RequestFilter("${sirene.api.prefix}${micronaut.http.wildcard}")
    void doFilterSirene(MutableHttpRequest<?> request) {
        if (!tokenMaintainer.hasValidTokenData()) {
            throw InseeHttpException.missingBearerToken();
        } else {
            request.bearerAuth(tokenMaintainer.bearer());
        }
    }
}
