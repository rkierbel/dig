package com.skilld;

import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import org.reactivestreams.Publisher;

import static io.micronaut.http.MediaType.APPLICATION_JSON;

@Client(id = HttpServiceId.INSEE)
interface InseeHttpClient {

    @Post("/token")
    @Consumes(APPLICATION_JSON)
    @SingleResult
    Publisher<InseeTokenResponse> token(@QueryValue(InseeConfig.GRANT_TYPE) String grantType);
}
