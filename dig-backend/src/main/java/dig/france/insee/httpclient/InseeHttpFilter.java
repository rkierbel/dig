package dig.france.insee.httpclient;

import dig.france.insee.InseeConstant;
import dig.france.insee.exception.InseeHttpException;
import dig.common.http.HttpServiceId;
import io.micronaut.context.BeanProvider;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.ClientFilter;
import io.micronaut.http.annotation.RequestFilter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@ClientFilter(serviceId = HttpServiceId.INSEE)
@Slf4j
class InseeHttpFilter {

    private final InseeHttpConfig config;
    private final BeanProvider<InseeHttpClient> inseeHttpClient;

    InseeHttpFilter(InseeHttpConfig config,
                    BeanProvider<InseeHttpClient> inseeHttpClient) {
        this.config = config;
        this.inseeHttpClient = inseeHttpClient;
    }

    @RequestFilter("${insee.api.token}${micronaut.http.wildcard}")
    public void doFilterInseeToken(MutableHttpRequest<?> request) {
        request.basicAuth(config.consumerKey(), config.consumerSecret());
    }

    @RequestFilter("${sirene.api.prefix}${micronaut.http.wildcard}")
    void doFilterSiren(MutableHttpRequest<?> request) {
        //TODO -> mechanism to fetch token every week - on each request, filter checks if token exists, then uses it
        //TODO -> save token + creation date in mem store -> only fetch new when now > creationDate + six days
        Mono.from(inseeHttpClient.get().token(InseeConstant.CLIENT_CREDENTIALS))
                .doOnError(InseeHttpException::logTokenGenerationFailure)
                .retry(InseeConstant.MAX_RETRY)
                .blockOptional()
                .orElseThrow(InseeHttpException::invalidTokenResponse)
                .asyncToken()
                .subscribe(request::bearerAuth);
    }
}
