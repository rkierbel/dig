package dig.france.insee.exception;

public class SireneSearchException extends InseeHttpException {

    public SireneSearchException(String message) {
        super(message);
    }

    public static SireneSearchException administrativeStatusNotFound(String adminStatusName) {
        throw new SireneSearchException(
                "Cannot find administrative status %s for a Sirene unit".formatted(adminStatusName));
    }

    public static SireneSearchException changeReasonNotFound(String reason) {
        throw new SireneSearchException(
                "The provided period change reason for a Sirene unit is not valid: %s".formatted(reason));
    }

    public static SireneSearchException searchOperatorNotFoundException(String opName) {
        throw new SireneSearchException(
                "Cannot find historicizedSearch operator %s for a Sirene historicizedSearch".formatted(opName));
    }

    public static SireneSearchException searchVariableNotFoundException(String searchVarName) {
        throw new SireneSearchException(
                "Cannot find historicizedSearch operator %s for a Sirene historicizedSearch".formatted(searchVarName));
    }

    public static SireneSearchException sireneSearchTypeNotFound(String searchTypeName) {
        throw new SireneSearchException("Cannot find provided search type %s".formatted(searchTypeName));
    }

    public static SireneSearchException historicizedNaturalPersonNameSearchFailure(String name) {
        throw new SireneSearchException(
                "Failed to perform historicized natural person search based on the name %s".formatted(name));
    }

    public static SireneSearchException historicizedSearchFailure(String criteria) {
        throw new SireneSearchException(
                "Failed to perform historicized multi-criteria search based on the criteria %s".formatted(criteria));

    }
}
