package france.insee.httpclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import reactor.core.publisher.Mono;

@Serdeable
public record InseeTokenResponse(@JsonProperty("access_token") String accessToken,
                                 @JsonProperty("token_type") String tokenType) {

    public static final String BEARER = "Bearer";

    Mono<String> asyncToken() {
        return Mono.just(this.accessToken);
    }
}
