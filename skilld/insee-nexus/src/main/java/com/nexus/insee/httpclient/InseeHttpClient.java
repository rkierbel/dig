package com.nexus.insee.httpclient;

import com.nexus.HttpServiceId;
import com.nexus.SirenInfoResponse;
import com.nexus.insee.InseeConstant;
import com.nexus.insee.exception.InseeHttpError;
import com.nexus.insee.sirenesearch.SireneSearchResponse;
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

    @Get("${siren.api.prefix}${siren.api.version-3}${siren.api.info}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @SingleResult
    Publisher<SirenInfoResponse> information();

    @Get("${siren.api.prefix}${siren.api.version-3-11}${siren.api.sirene-search}")
    //q=periode(nomUniteLegale:grzeszezak) or q=raisonSociale:blabla
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @SingleResult
    Publisher<SireneSearchResponse> search(@QueryValue(value = InseeConstant.QUERY) String sireneSearch);
}
