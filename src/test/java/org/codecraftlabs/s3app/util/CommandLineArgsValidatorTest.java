package org.codecraftlabs.s3app.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.codecraftlabs.s3app.util.CommandLineArgsValidator.validateCommandLineArgs;
import static org.codecraftlabs.s3app.util.CommandLineS3Service.CREATE_BUCKET;
import static org.codecraftlabs.s3app.util.CommandLineUtil.AWS_REGION_LONG_OPT;
import static org.codecraftlabs.s3app.util.CommandLineUtil.S3_SERVICE_LONG_OPT;

public class CommandLineArgsValidatorTest {
    @Test
    void validArgs() {
        Map<String, String> args = new HashMap<>();
        args.put(S3_SERVICE_LONG_OPT, CREATE_BUCKET.code());
        args.put(AWS_REGION_LONG_OPT, "us-east-1");

        Assertions.assertDoesNotThrow(() -> {
            validateCommandLineArgs(args);
        });
    }

    @Test
    void emptyServiceArgument() {
        Map<String, String> args = new HashMap<>();
        args.put(S3_SERVICE_LONG_OPT, "");
        args.put(AWS_REGION_LONG_OPT, "us-east-1");

        Assertions.assertThrows(InvalidArgumentException.class, () -> {
            validateCommandLineArgs(args);
        });
    }

    @Test
    void nullServiceArgument() {
        Map<String, String> args = new HashMap<>();
        args.put(S3_SERVICE_LONG_OPT, null);
        args.put(AWS_REGION_LONG_OPT, "us-east-1");

        Assertions.assertThrows(InvalidArgumentException.class, () -> {
            validateCommandLineArgs(args);
        });
    }

    @Test
    void invalidServiceArgument() {
        Map<String, String> args = new HashMap<>();
        args.put(S3_SERVICE_LONG_OPT, "blabla");
        args.put(AWS_REGION_LONG_OPT, "us-east-1");

        Assertions.assertThrows(InvalidArgumentException.class, () -> {
            validateCommandLineArgs(args);
        });
    }

    @Test
    void emptyRegionArgument() {
        Map<String, String> args = new HashMap<>();
        args.put(S3_SERVICE_LONG_OPT, CREATE_BUCKET.code());
        args.put(AWS_REGION_LONG_OPT, "");

        Assertions.assertThrows(InvalidArgumentException.class, () -> {
            validateCommandLineArgs(args);
        });
    }

    @Test
    void nullRegionArgument() {
        Map<String, String> args = new HashMap<>();
        args.put(S3_SERVICE_LONG_OPT, CREATE_BUCKET.code());
        args.put(AWS_REGION_LONG_OPT, null);

        Assertions.assertThrows(InvalidArgumentException.class, () -> {
            validateCommandLineArgs(args);
        });
    }

    @Test
    void invalidRegionArgument() {
        Map<String, String> args = new HashMap<>();
        args.put(S3_SERVICE_LONG_OPT, CREATE_BUCKET.code());
        args.put(AWS_REGION_LONG_OPT, "invalid-code");

        Assertions.assertThrows(InvalidArgumentException.class, () -> {
            validateCommandLineArgs(args);
        });
    }
}
