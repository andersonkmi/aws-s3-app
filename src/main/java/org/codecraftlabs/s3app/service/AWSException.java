package org.codecraftlabs.s3app.service;

import javax.annotation.Nonnull;

public class AWSException extends Exception {
    public AWSException(@Nonnull String message) {
        super(message);
    }

    public AWSException(@Nonnull String message, @Nonnull Throwable exception) {
        super(message, exception);
    }
}
