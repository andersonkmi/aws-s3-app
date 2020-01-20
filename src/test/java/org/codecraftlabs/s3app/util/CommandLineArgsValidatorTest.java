package org.codecraftlabs.s3app.util;

import org.codecraftlabs.s3app.validator.CommandLineArgsValidator;
import org.codecraftlabs.s3app.validator.InvalidArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.codecraftlabs.s3app.util.AppArguments.REGION_OPTION;
import static org.codecraftlabs.s3app.util.AppArguments.SERVICE_OPTION;
import static org.codecraftlabs.s3app.util.CommandLineS3Service.CREATE_BUCKET;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("CommandLineArgsValidator unit tests")
public class CommandLineArgsValidatorTest {
    private CommandLineArgsValidator validator;

    @BeforeEach
    public void setup() {
        validator = new CommandLineArgsValidator();
    }

    @Test
    @DisplayName("When the arguments are valid")
    void validArgs() {
        Map<String, String> args = new HashMap<>();
        args.put(SERVICE_OPTION, CREATE_BUCKET.code());
        args.put(REGION_OPTION, "us-east-1");
        var appArgs = new AppArguments(args);

        assertDoesNotThrow(() -> validator.validateCommandLineArgs(appArgs));
    }

    @Test
    @DisplayName("When the --service option has an empty value")
    void emptyServiceArgument() {
        Map<String, String> args = new HashMap<>();
        args.put(SERVICE_OPTION, "");
        args.put(REGION_OPTION, "us-east-1");
        var appArgs = new AppArguments(args);

        assertThrows(InvalidArgumentException.class, () -> validator.validateCommandLineArgs(appArgs));
    }

    @Test
    @DisplayName("When --service option has a null value")
    void nullServiceArgument() {
        Map<String, String> args = new HashMap<>();
        args.put(SERVICE_OPTION, null);
        args.put(REGION_OPTION, "us-east-1");
        var appArgs = new AppArguments(args);

        assertThrows(InvalidArgumentException.class, () -> validator.validateCommandLineArgs(appArgs));
    }

    @Test
    @DisplayName("When the --service option has an invalid value")
    void invalidServiceArgument() {
        Map<String, String> args = new HashMap<>();
        args.put(SERVICE_OPTION, "blabla");
        args.put(REGION_OPTION, "us-east-1");
        var appArgs = new AppArguments(args);

        assertThrows(InvalidArgumentException.class, () -> validator.validateCommandLineArgs(appArgs));
    }

    @Test
    @DisplayName("When the --region option has an empty value")
    void emptyRegionArgument() {
        Map<String, String> args = new HashMap<>();
        args.put(SERVICE_OPTION, CREATE_BUCKET.code());
        args.put(REGION_OPTION, "");
        var appArgs = new AppArguments(args);

        assertThrows(InvalidArgumentException.class, () -> validator.validateCommandLineArgs(appArgs));
    }

    @Test
    @DisplayName("When the --region option has a null value")
    void nullRegionArgument() {
        Map<String, String> args = new HashMap<>();
        args.put(SERVICE_OPTION, CREATE_BUCKET.code());
        args.put(REGION_OPTION, null);
        var appArgs = new AppArguments(args);

        assertThrows(InvalidArgumentException.class, () -> validator.validateCommandLineArgs(appArgs));
    }

    @Test
    @DisplayName("When the --region option has an invalid value")
    void invalidRegionArgument() {
        Map<String, String> args = new HashMap<>();
        args.put(SERVICE_OPTION, CREATE_BUCKET.code());
        args.put(REGION_OPTION, "invalid-code");
        var appArgs = new AppArguments(args);

        assertThrows(InvalidArgumentException.class, () -> validator.validateCommandLineArgs(appArgs));
    }
}
