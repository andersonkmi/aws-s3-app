package org.codecraftlabs.s3app.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.util.AppArguments;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

public class AppArgsValidator {
    private static final Logger logger = LogManager.getLogger(AppArgsValidator.class);

    private Set<AppArgumentsValidationPolicy> policies;

    private AppArgsValidator() {
        policies = new LinkedHashSet<>();
    }

    public static AppArgsValidator build() {
        AppArgsValidator instance = new AppArgsValidator();
        instance.policies.add(new RegionOptionValidationPolicy());
        instance.policies.add(new ServiceOptionValidationPolicy());
        return instance;
    }

    public void validate(@Nonnull AppArguments args) throws InvalidArgumentException {
        logger.info("Command line validation in progress");

        for (AppArgumentsValidationPolicy policy : policies) {
            policy.verify(args);
        }
    }
}
