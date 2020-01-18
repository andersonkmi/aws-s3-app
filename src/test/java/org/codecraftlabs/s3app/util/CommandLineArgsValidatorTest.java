package org.codecraftlabs.s3app.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.codecraftlabs.s3app.util.CommandLineArgsValidator.validateCommandLineArgs;
import static org.codecraftlabs.s3app.util.CommandLineS3Service.CREATE_BUCKET;
import static org.codecraftlabs.s3app.util.CommandLineUtil.AWS_REGION_LONG_OPT;
import static org.codecraftlabs.s3app.util.CommandLineUtil.S3_SERVICE_LONG_OPT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("CommandLineArgsValidator unit tests")
public class CommandLineArgsValidatorTest {
    @Test
    @DisplayName("When the arguments are valid")
    void validArgs() {
        Map<String, String> args = new HashMap<>();
        args.put(S3_SERVICE_LONG_OPT, CREATE_BUCKET.code());
        args.put(AWS_REGION_LONG_OPT, "us-east-1");
        var appArgs = new AppArguments(args);

        assertDoesNotThrow(() -> validateCommandLineArgs(appArgs));
    }

    @Test
    @DisplayName("When the --service option has an empty value")
    void emptyServiceArgument() {
        Map<String, String> args = new HashMap<>();
        args.put(S3_SERVICE_LONG_OPT, "");
        args.put(AWS_REGION_LONG_OPT, "us-east-1");
        var appArgs = new AppArguments(args);

        assertThrows(InvalidArgumentException.class, () -> validateCommandLineArgs(appArgs));
    }

    @Test
    @DisplayName("When --service option has a null value")
    void nullServiceArgument() {
        Map<String, String> args = new HashMap<>();
        args.put(S3_SERVICE_LONG_OPT, null);
        args.put(AWS_REGION_LONG_OPT, "us-east-1");
        var appArgs = new AppArguments(args);

        assertThrows(InvalidArgumentException.class, () -> validateCommandLineArgs(appArgs));
    }

    @Test
    @DisplayName("When the --service option has an invalid value")
    void invalidServiceArgument() {
        Map<String, String> args = new HashMap<>();
        args.put(S3_SERVICE_LONG_OPT, "blabla");
        args.put(AWS_REGION_LONG_OPT, "us-east-1");
        var appArgs = new AppArguments(args);

        assertThrows(InvalidArgumentException.class, () -> validateCommandLineArgs(appArgs));
    }

    @Test
    @DisplayName("When the --region option has an empty value")
    void emptyRegionArgument() {
        Map<String, String> args = new HashMap<>();
        args.put(S3_SERVICE_LONG_OPT, CREATE_BUCKET.code());
        args.put(AWS_REGION_LONG_OPT, "");
        var appArgs = new AppArguments(args);

        assertThrows(InvalidArgumentException.class, () -> validateCommandLineArgs(appArgs));
    }

    @Test
    @DisplayName("When the --region option has a null value")
    void nullRegionArgument() {
        Map<String, String> args = new HashMap<>();
        args.put(S3_SERVICE_LONG_OPT, CREATE_BUCKET.code());
        args.put(AWS_REGION_LONG_OPT, null);
        var appArgs = new AppArguments(args);

        assertThrows(InvalidArgumentException.class, () -> validateCommandLineArgs(appArgs));
    }

    @Test
    @DisplayName("When the --region option has an invalid value")
    void invalidRegionArgument() {
        Map<String, String> args = new HashMap<>();
        args.put(S3_SERVICE_LONG_OPT, CREATE_BUCKET.code());
        args.put(AWS_REGION_LONG_OPT, "invalid-code");
        var appArgs = new AppArguments(args);

        assertThrows(InvalidArgumentException.class, () -> validateCommandLineArgs(appArgs));
    }
}
