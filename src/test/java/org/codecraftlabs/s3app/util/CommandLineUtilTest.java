package org.codecraftlabs.s3app.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.codecraftlabs.s3app.util.AppArguments.BUCKET_OPTION;
import static org.codecraftlabs.s3app.util.AppArguments.REGION_OPTION;
import static org.codecraftlabs.s3app.util.AppArguments.SERVICE_OPTION;
import static org.codecraftlabs.s3app.util.CommandLineUtil.parse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("CommandLineUtil class unit tests")
public class CommandLineUtilTest {
    @Test
    @DisplayName("When the args are all ok")
    void parseArgsOk() {
        String[] args = {"-s", "listBuckets", "-r", "us-east-1", "-b", "test"};
        assertDoesNotThrow(() -> {
            var options = parse(args);
            assertThat(options.option(SERVICE_OPTION), is("listBuckets"));
            assertThat(options.option(REGION_OPTION), is("us-east-1"));
            assertThat(options.option(BUCKET_OPTION), is("test"));
        });
    }

    @Test
    @DisplayName("When the args are all ok")
    void parseArgsMinimumRequiredOk() {
        String[] args = {"-s", "listBuckets", "-r", "us-east-1"};
        assertDoesNotThrow(() -> {
            var options = parse(args);
            assertThat(options.option(SERVICE_OPTION), is("listBuckets"));
            assertThat(options.option(REGION_OPTION), is("us-east-1"));
        });
    }

    @Test
    @DisplayName("When -s option is missing")
    void missingServiceOption() {
        String[] args = {"-r", "us-east-1", "-b", "test"};
        assertThrows(CommandLineException.class, () -> parse(args));
    }

    @Test
    @DisplayName("When -r option is missing")
    void missingRegionOption() {
        String[] args = {"-s", "listBuckets", "-b", "test"};
        assertThrows(CommandLineException.class, () -> parse(args));
    }
}
