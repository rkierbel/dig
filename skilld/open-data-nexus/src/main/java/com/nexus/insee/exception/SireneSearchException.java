package com.nexus.insee.exception;

public class SireneSearchException extends InseeHttpException {

    public SireneSearchException(String message) {
        super(message);
    }

    public static SireneSearchException administrativeStatusNotFound(String name) {
        throw new SireneSearchException(
                String.format("Cannot find administrative status %s for a Siren unit.", name)
        );
    }

    public static SireneSearchException changeReasonNotFound(String reason) {
        throw new SireneSearchException(
                String.format("The provided period change reason for a Sirene unit is not valid: %s.", reason)
        );
    }

    public static SireneSearchException searchOperatorNotFoundException(String name) {
        throw new SireneSearchException(
                String.format("Cannot find search operator %s for a Sirene search.", name)
        );
    }

    public static SireneSearchException searchVariableNotFoundException(String name) {
        throw new SireneSearchException(
                String.format("Cannot find search operator %s for a Sirene search.", name)
        );
    }
}
