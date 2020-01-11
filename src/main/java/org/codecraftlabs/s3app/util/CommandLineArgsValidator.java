package org.codecraftlabs.s3app.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.data.AWSRegion;

import java.util.Map;
import java.util.Optional;

import static org.codecraftlabs.s3app.util.CommandLineUtil.AWS_REGION_LONG_OPT;
import static org.codecraftlabs.s3app.util.CommandLineUtil.S3_SERVICE_LONG_OPT;

public class CommandLineArgsValidator {
    private static final Logger logger = LogManager.getLogger(CommandLineArgsValidator.class);

    public static void validateCommandLineArgs(Map<String, String> args) throws InvalidArgumentException {
        logger.info("Command line validation in progress");

        if (args == null || args.isEmpty()) {
            return;
        }

        validateServiceOption(args.get(S3_SERVICE_LONG_OPT));
        validateRegionOption(args.get(AWS_REGION_LONG_OPT));
    }

    private static void validateServiceOption(String value) throws InvalidArgumentException {
        var operation = CommandLineS3Service.findByCode(value);
        if (operation.isEmpty()) {
            throw new InvalidArgumentException(String.format("Invalid service: '%s'", value));
        }
    }

    private static void validateRegionOption(String regionValue) throws InvalidArgumentException {
        var region = AWSRegion.findByCode(regionValue);
        if (region.isEmpty()) {
            throw new InvalidArgumentException(String.format("Invalid region: '%s'", regionValue));
        }
    }
}
