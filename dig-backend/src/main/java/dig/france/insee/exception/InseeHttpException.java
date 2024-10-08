package dig.france.insee.exception;

import io.micronaut.http.exceptions.HttpException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InseeHttpException extends HttpException {

    public InseeHttpException(String message) {
        super(message);
    }

    public static InseeHttpException invalidTokenResponse() {
        throw new InseeHttpException("Could not generate token for the current client credentials");
    }

    public static InseeHttpException missingBearerToken() {
        throw new InseeHttpException("Could not proceed with the search request: missing bearer token");
    }

    public static void logTokenGenerationFailure(Throwable ex) {
        log.warn("An error occurred while generating a token fromApiResponse Insee's API with cause {}\nand message {}",
                ex.getCause(), ex.getMessage());
    }

    public static void logSireneSearchFailure(Throwable ex) {
        log.warn("An error occurred while performing a Sirene historicizedSearch with cause {}\nand message {}",
                ex.getCause(), ex.getMessage());
    }
}
