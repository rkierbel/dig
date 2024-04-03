package com.skilld;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;

@ConfigurationProperties(InseeConfig.PREFIX)
@Requires(property = InseeConfig.PREFIX)
record InseeConfig(String consumerKey,
                   String consumerSecret) {


    static final String PREFIX = "insee";
    static final String CLIENT_CREDENTIALS = "client_credentials";
    static final String GRANT_TYPE = "grant_type";
}
