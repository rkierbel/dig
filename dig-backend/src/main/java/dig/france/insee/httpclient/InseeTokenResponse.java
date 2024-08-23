package dig.france.insee.httpclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record InseeTokenResponse(@JsonProperty("access_token") String accessToken,
                                 @JsonProperty("token_type") String tokenType) {

    public static final String BEARER = "Bearer";
}
