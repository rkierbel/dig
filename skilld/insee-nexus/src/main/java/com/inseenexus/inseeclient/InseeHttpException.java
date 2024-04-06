package com.inseenexus.inseeclient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InseeHttpException extends RuntimeException {
    public static InseeHttpException invalidTokenResponse() {
        throw new InseeHttpException(); //TODO -> message + extract base messages in constants
    }

    public static void logTokenGenerationFailure(Throwable ex) {
        log.warn("An error occurred when generating a token from Insee's API with cause " + ex.getCause() + "\nand message "+ ex.getMessage());
    }
}
