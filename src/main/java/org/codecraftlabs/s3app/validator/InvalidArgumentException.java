package org.codecraftlabs.s3app.validator;

public class InvalidArgumentException extends Exception {
    public InvalidArgumentException(String message) {
        super(message);
    }

    public InvalidArgumentException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
