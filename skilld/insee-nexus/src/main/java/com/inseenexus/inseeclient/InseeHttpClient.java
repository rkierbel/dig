package com.inseenexus.inseeclient;

import com.inseenexus.HttpServiceId;
import com.inseenexus.SirenInfoResponse;
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
interface InseeHttpClient {

    @Post("${insee.api.token}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @SingleResult
    Publisher<InseeTokenResponse> token(@QueryValue(value = InseeHttpConfig.GRANT_TYPE) String grantType);

    @Get("${siren.api.prefix}${siren.api.version-3}${siren.api.info}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @SingleResult
    Publisher<SirenInfoResponse> information();

    @Get("${siren.api.prefix}${siren.api.version-3-11}${siren.api.siren-search}")
    //q=periode(nomUniteLegale:grzeszezak) or q=raisonSociale:blabla
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @SingleResult
    Publisher<SirenSearchResponse> search(@QueryValue(value = InseeHttpConfig.QUERY) String sirenSearch);
}
