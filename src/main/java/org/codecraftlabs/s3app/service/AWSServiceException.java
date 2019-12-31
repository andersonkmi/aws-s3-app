package org.codecraftlabs.s3app.service;

public class AWSServiceException extends Exception {
    public AWSServiceException(String message) {
        super(message);
    }

    public AWSServiceException(String message, Throwable exception) {
        super(message, exception);
    }
}
