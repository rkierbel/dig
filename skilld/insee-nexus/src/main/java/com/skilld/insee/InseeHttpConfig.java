package com.skilld.insee;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;

@ConfigurationProperties(InseeHttpConfig.PREFIX)
@Requires(property = InseeHttpConfig.PREFIX)
record InseeHttpConfig(String consumerKey,
                       String consumerSecret) {


    static final String PREFIX = "insee";
    static final String CLIENT_CREDENTIALS = "client_credentials";
    static final String GRANT_TYPE = "grant_type";
    static final String QUERY = "q";
}
