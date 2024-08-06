package france.insee.httpclient;

import france.insee.InseeConstant;
import france.insee.exception.InseeHttpError;
import france.insee.sirene.SirenInfoResponse;
import france.insee.sirene.search.SireneSearchResponse;
import common.http.HttpServiceId;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import org.reactivestreams.Publisher;

@Client(id = HttpServiceId.INSEE,
        errorType = InseeHttpError.class)
public interface InseeHttpClient {

    @Post("${insee.api.token}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @SingleResult
    Publisher<InseeTokenResponse> token(@QueryValue(value = InseeConstant.GRANT_TYPE) String grantType);

    @Get("${sirene.api.prefix}${sirene.api.version-3}${sirene.api.info}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @SingleResult
    Publisher<SirenInfoResponse> information();

    @Get("${sirene.api.prefix}${sirene.api.version-3-11}${sirene.api.sirene-search}")
    //q=periode(nomUniteLegale:grzeszezak) or q=raisonSociale:blabla
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @SingleResult
    Publisher<SireneSearchResponse> search(@QueryValue(value = InseeConstant.QUERY) String sireneSearch);
}
