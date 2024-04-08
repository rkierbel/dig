package com.nexus.insee;

public class SirenSearchException extends InseeHttpException {

    public SirenSearchException(String message) {
        super(message);
    }

    public static SirenSearchException administrativeStatusNotFound(String name) {
        throw new SirenSearchException("Cannot find administrative status " + name + " for a Siren unit.");
    }
}
