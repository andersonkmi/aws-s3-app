package org.codecraftlabs.s3app.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.data.AWSRegion;
import org.codecraftlabs.s3app.util.AppArguments;
import org.codecraftlabs.s3app.util.CommandLineS3Service;

import javax.annotation.Nonnull;

import static org.codecraftlabs.s3app.util.AppArguments.REGION_OPTION;
import static org.codecraftlabs.s3app.util.AppArguments.SERVICE_OPTION;

public class CommandLineArgsValidator {
    private static final Logger logger = LogManager.getLogger(CommandLineArgsValidator.class);

    public void validateCommandLineArgs(@Nonnull AppArguments args) throws InvalidArgumentException {
        logger.info("Command line validation in progress");

        validateServiceOption(args.option(SERVICE_OPTION));
        validateRegionOption(args.option(REGION_OPTION));
    }

    private void validateServiceOption(String value) throws InvalidArgumentException {
        var operation = CommandLineS3Service.findByCode(value);
        if (operation.isEmpty()) {
            throw new InvalidArgumentException(String.format("Invalid service: '%s'", value));
        }
    }

    private void validateRegionOption(String regionValue) throws InvalidArgumentException {
        var region = AWSRegion.findByCode(regionValue);
        if (region.isEmpty()) {
            throw new InvalidArgumentException(String.format("Invalid region: '%s'", regionValue));
        }
    }
}
