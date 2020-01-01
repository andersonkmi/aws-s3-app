package org.codecraftlabs.s3app.service;

public class AWSException extends Exception {
    public AWSException(String message) {
        super(message);
    }

    public AWSException(String message, Throwable exception) {
        super(message, exception);
    }
}
