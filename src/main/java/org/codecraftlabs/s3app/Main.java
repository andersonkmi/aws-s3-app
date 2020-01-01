package org.codecraftlabs.s3app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.data.S3Bucket;
import org.codecraftlabs.s3app.service.AWSException;
import org.codecraftlabs.s3app.service.AWSS3;

import java.util.Set;

import static org.codecraftlabs.s3app.data.AWSRegion.US_EAST_1;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting the app");
        try {
            S3Bucket bucket = new S3Bucket("codecraftlabs-" + System.currentTimeMillis(), US_EAST_1);
            AWSS3.create(bucket);

            Set<S3Bucket> buckets = AWSS3.buckets();
            buckets.forEach(logger::info);
            logger.info("App finished OK!");
        } catch (AWSException exception) {
            logger.error(exception.getMessage(), exception);
        }
    }
}
