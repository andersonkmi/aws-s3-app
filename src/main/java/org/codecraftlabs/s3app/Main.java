package org.codecraftlabs.s3app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.data.S3Bucket;
import org.codecraftlabs.s3app.service.AWSException;
import org.codecraftlabs.s3app.service.S3BucketListService;

import java.util.Set;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting the app");
        try {
            S3BucketListService service = new S3BucketListService();
            Set<S3Bucket> buckets = service.buckets();
            buckets.forEach(logger::info);
            logger.info("App finished OK!");
        } catch (AWSException exception) {
            logger.error(exception.getMessage(), exception);
        }
    }
}
