package com.skilld.bff.insee.exception;

public class SireneSearchException extends RuntimeException {

    public SireneSearchException(String message) {
        super(message);
    }

    public static SireneSearchException searchOperatorNotFoundException(String name) {
        throw new SireneSearchException(
                String.format("Cannot find search operator %s for a Sirene search.", name)
        );
    }
}
