package org.codecraftlabs.s3app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.data.S3Bucket;
import org.codecraftlabs.s3app.service.AwsException;
import org.codecraftlabs.s3app.service.AwsS3;

import java.util.Set;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            AwsS3 s3 = new AwsS3();
            Set<S3Bucket> buckets = s3.buckets();
            buckets.forEach(logger::info);
        } catch (AwsException exception) {
            logger.error(exception.getMessage(), exception);
        }
    }
}
