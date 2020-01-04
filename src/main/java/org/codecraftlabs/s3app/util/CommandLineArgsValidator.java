package org.codecraftlabs.s3app.util;

import org.codecraftlabs.s3app.data.AWSRegion;

import java.util.Map;

public class CommandLineArgsValidator {
    public static void validateCommandLineArgs(Map<String, String> args) throws InvalidArgumentException {
        if (args == null || args.isEmpty()) {
            return;
        }

        if (args.containsKey(CommandLineUtil.S3_SERVICE_LONG_OPT)) {
            String value = args.get(CommandLineUtil.S3_SERVICE_LONG_OPT);
            S3Operation operation = S3Operation.findByCode(value);
            if (operation == null) {
                throw new InvalidArgumentException(String.format("Invalid service: '%s'", value));
            }
        }

        if (args.containsKey(CommandLineUtil.AWS_REGION_LONG_OPT)) {
            String value = args.get(CommandLineUtil.AWS_REGION_LONG_OPT);
            AWSRegion region = AWSRegion.findByCode(value);
            if (region == null) {
                throw new InvalidArgumentException(String.format("Invalid region: '%s'", value));
            }
        }
    }
}
