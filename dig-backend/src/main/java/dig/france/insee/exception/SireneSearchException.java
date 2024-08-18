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

    public static SireneSearchException searchOperatorNotFound(String opName) {
        throw new SireneSearchException(
                "Cannot find historicizedSearch operator %s for a Sirene historicizedSearch".formatted(opName));
    }

    public static SireneSearchException searchVariableNotFound(String searchVarName) {
        throw new SireneSearchException(
                "Cannot find historicizedSearch operator %s for a Sirene historicizedSearch".formatted(searchVarName));
    }

    public static SireneSearchException sireneSearchTypeNotFound(String searchTypeName) {
        throw new SireneSearchException("Cannot find provided search type %s".formatted(searchTypeName));
    }

    public static SireneSearchException historicizedSearchFailure(String criteria) {
        throw new SireneSearchException(
                "Failed to perform historicized search based on the criteria %s".formatted(criteria));

    }

    public static SireneSearchException unitTypeNotFound(String type) {
        throw new SireneSearchException("Cannot find unit type %s for a Sirene unit".formatted(type));
    }

    public static SireneSearchException nullParameterForSiretSearch() {
        throw new SireneSearchException("Cannot perform search on Siret register: null Siren number set");
    }
}
