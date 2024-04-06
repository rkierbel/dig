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
        errorType = com.inseenexus.inseeclient.InseeHttpError.class)
interface InseeHttpClient {

    @Post("${insee.api.token}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @SingleResult
    Publisher<com.inseenexus.inseeclient.InseeTokenResponse> token(@QueryValue(value = com.inseenexus.inseeclient.InseeHttpConfig.GRANT_TYPE) String grantType);

    @Get("${siren.api.prefix}${siren.api.info}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @SingleResult
    Publisher<SirenInfoResponse> information();

    @Get("${siren.api.prefix}${siren.api.siren-search}") //q=periode(nomUniteLegale:grzeszezak) or q=raisonSociale:blabla
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @SingleResult
    Publisher<com.inseenexus.inseeclient.SirenSearchResponse> search(@QueryValue(value= com.inseenexus.inseeclient.InseeHttpConfig.QUERY) String searchRequest);
}
