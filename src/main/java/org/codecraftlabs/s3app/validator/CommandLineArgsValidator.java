package org.codecraftlabs.s3app.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.util.AppArguments;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

public class CommandLineArgsValidator {
    private static final Logger logger = LogManager.getLogger(CommandLineArgsValidator.class);

    private Set<AppArgumentsValidationPolicy> policies;

    public CommandLineArgsValidator() {
        policies = new LinkedHashSet<>();
    }

    public void add(AppArgumentsValidationPolicy policy) {
        policies.add(policy);
    }

    public void validate(@Nonnull AppArguments args) throws InvalidArgumentException {
        logger.info("Command line validation in progress");

        for (AppArgumentsValidationPolicy policy : policies) {
            policy.verify(args);
        }
    }
}
