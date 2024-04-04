package com.skilld;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.client.exceptions.HttpClientException;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {HttpClientException.class, InseeHttpException.class})
public class HttpExceptionHandler implements ExceptionHandler<InseeHttpException, HttpResponse<InseeHttpError>> {

    @Override
    public HttpResponse<InseeHttpError> handle(HttpRequest request, InseeHttpException exception) {
        return HttpResponse.badRequest();
    }
}
