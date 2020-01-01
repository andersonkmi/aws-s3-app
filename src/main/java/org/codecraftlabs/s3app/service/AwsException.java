package org.codecraftlabs.s3app.service;

public class AwsException extends Exception {
    public AwsException(String message) {
        super(message);
    }

    public AwsException(String message, Throwable exception) {
        super(message, exception);
    }
}
