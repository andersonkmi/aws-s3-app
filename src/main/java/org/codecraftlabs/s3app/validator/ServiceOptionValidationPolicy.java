package org.codecraftlabs.s3app.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.util.AppArguments;

import javax.annotation.Nonnull;

import static java.lang.String.format;
import static org.codecraftlabs.s3app.util.AppArguments.SERVICE_OPTION;
import static org.codecraftlabs.s3app.util.CommandLineS3Service.findByCode;

public class ServiceOptionValidationPolicy implements AppArgumentsValidationPolicy {
    private static final Logger logger = LogManager.getLogger(ServiceOptionValidationPolicy.class);

    @Override
    public void verify(@Nonnull AppArguments args) throws InvalidArgumentException {
        logger.info("Applying service option validation policy");
        var operation = findByCode(args.option(SERVICE_OPTION));
        if (operation.isEmpty()) {
            throw new InvalidArgumentException(format("Invalid service: '%s'", args.option(SERVICE_OPTION)));
        }
    }
}
