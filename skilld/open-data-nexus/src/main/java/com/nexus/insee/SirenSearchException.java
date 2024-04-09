package com.nexus.insee;

public class SirenSearchException extends InseeHttpException {

    public SirenSearchException(String message) {
        super(message);
    }

    public static SirenSearchException administrativeStatusNotFound(String name) {
        throw new SirenSearchException(
                String.format("Cannot find administrative status %s for a Siren unit.", name)
        );
    }

    public static RuntimeException changeReasonNotFound(String reason) {
        throw new SirenSearchException(
                String.format("The provided period change reason for a Sirene unit is not valid: %s.", reason)
        );
    }
}
