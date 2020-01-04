package org.codecraftlabs.s3app.util;

public class CommandLineException extends Exception {
    public CommandLineException(String message) {
        super(message);
    }

    public CommandLineException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
