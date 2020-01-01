package org.codecraftlabs.s3app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.data.S3Bucket;
import org.codecraftlabs.s3app.service.AWSServiceException;
import org.codecraftlabs.s3app.service.AwsS3;

import java.util.Set;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        try {
            AwsS3 s3 = new AwsS3();
            Set<S3Bucket> buckets = s3.listAllBuckets();
            buckets.forEach(logger::info);
        } catch (AWSServiceException exception) {
            logger.error(exception.getMessage(), exception);
        }
    }
}
