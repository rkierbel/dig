package france.insee.exception;

public class SireneSearchException extends InseeHttpException {

    public SireneSearchException(String message) {
        super(message);
    }

    public static SireneSearchException administrativeStatusNotFound(String name) {
        throw new SireneSearchException("Cannot find administrative status %s for a Sirene unit".formatted(name));
    }

    public static SireneSearchException changeReasonNotFound(String reason) {
        throw new SireneSearchException("The provided period change reason for a Sirene unit is not valid: %s".formatted(reason));
    }

    public static SireneSearchException searchOperatorNotFoundException(String name) {
        throw new SireneSearchException("Cannot find search operator %s for a Sirene search".formatted(name));
    }

    public static SireneSearchException searchVariableNotFoundException(String name) {
        throw new SireneSearchException("Cannot find search operator %s for a Sirene search".formatted(name));
    }
}
