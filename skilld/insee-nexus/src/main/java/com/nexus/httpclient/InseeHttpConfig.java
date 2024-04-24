package com.nexus.httpclient;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;

@ConfigurationProperties(InseeHttpConfig.PREFIX)
@Requires(property = InseeHttpConfig.PREFIX)
record InseeHttpConfig(String consumerKey,
                       String consumerSecret) {


    static final String PREFIX = "insee";
}
