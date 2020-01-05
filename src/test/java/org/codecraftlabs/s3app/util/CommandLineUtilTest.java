package org.codecraftlabs.s3app.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.codecraftlabs.s3app.util.CommandLineUtil.AWS_REGION_LONG_OPT;
import static org.codecraftlabs.s3app.util.CommandLineUtil.S3_BUCKET_NAME_LONG_OPT;
import static org.codecraftlabs.s3app.util.CommandLineUtil.S3_SERVICE_LONG_OPT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("CommandLineUtil class unit tests")
public class CommandLineUtilTest {
    private CommandLineUtil service;

    @BeforeEach
    void setup() {
        service = new CommandLineUtil();
    }

    @Test
    @DisplayName("When the args are all ok")
    void parseArgsOk() {
        String[] args = {"-s", "listBucket", "-r", "us-east-1", "-b", "test"};
        assertDoesNotThrow(() -> service.parse(args));
        Map<String, String> options = service.options();
        assertThat(options, hasKey(S3_SERVICE_LONG_OPT));
        assertThat(options, hasKey(AWS_REGION_LONG_OPT));
        assertThat(options, hasKey(S3_BUCKET_NAME_LONG_OPT));
    }
}
