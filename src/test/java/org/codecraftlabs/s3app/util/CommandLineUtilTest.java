package org.codecraftlabs.s3app.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.codecraftlabs.s3app.util.CommandLineUtil.AWS_REGION_LONG_OPT;
import static org.codecraftlabs.s3app.util.CommandLineUtil.S3_BUCKET_NAME_LONG_OPT;
import static org.codecraftlabs.s3app.util.CommandLineUtil.S3_SERVICE_LONG_OPT;
import static org.codecraftlabs.s3app.util.CommandLineUtil.parse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("CommandLineUtil class unit tests")
public class CommandLineUtilTest {
    @Test
    @DisplayName("When the args are all ok")
    void parseArgsOk() {
        String[] args = {"-s", "listBucket", "-r", "us-east-1", "-b", "test"};
        assertDoesNotThrow(() -> {
            Map<String, String> options = parse(args);
            assertThat(options, hasKey(S3_SERVICE_LONG_OPT));
            assertThat(options, hasEntry(S3_SERVICE_LONG_OPT, "listBucket"));
            assertThat(options, hasKey(AWS_REGION_LONG_OPT));
            assertThat(options, hasEntry(AWS_REGION_LONG_OPT, "us-east-1"));
            assertThat(options, hasKey(S3_BUCKET_NAME_LONG_OPT));
            assertThat(options, hasEntry(S3_BUCKET_NAME_LONG_OPT, "test"));
        });
    }

    @Test
    @DisplayName("When the args are all ok")
    void parseArgsMinimumRequiredOk() {
        String[] args = {"-s", "listBucket", "-r", "us-east-1"};
        assertDoesNotThrow(() -> {
            Map<String, String> options = parse(args);
            assertThat(options, hasKey(S3_SERVICE_LONG_OPT));
            assertThat(options, hasEntry(S3_SERVICE_LONG_OPT, "listBucket"));
            assertThat(options, hasKey(AWS_REGION_LONG_OPT));
            assertThat(options, hasEntry(AWS_REGION_LONG_OPT, "us-east-1"));
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
        String[] args = {"-s", "listBucket", "-b", "test"};
        assertThrows(CommandLineException.class, () -> parse(args));
    }
}
