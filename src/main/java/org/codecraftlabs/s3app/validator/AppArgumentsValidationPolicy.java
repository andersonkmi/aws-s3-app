package org.codecraftlabs.s3app.validator;

import org.codecraftlabs.s3app.util.AppArguments;

import javax.annotation.Nonnull;

public interface AppArgumentsValidationPolicy {
    void verify(@Nonnull AppArguments args) throws InvalidArgumentException;
}
