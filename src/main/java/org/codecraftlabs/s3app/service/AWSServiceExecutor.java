package org.codecraftlabs.s3app.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.data.AWSRegion;
import org.codecraftlabs.s3app.data.S3Bucket;
import org.codecraftlabs.s3app.util.CommandLineS3Service;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;

import static org.codecraftlabs.s3app.util.CommandLineS3Service.CREATE_BUCKET;
import static org.codecraftlabs.s3app.util.CommandLineS3Service.DELETE_BUCKET;
import static org.codecraftlabs.s3app.util.CommandLineS3Service.LIST_BUCKET;
import static org.codecraftlabs.s3app.util.CommandLineS3Service.LIST_OBJECTS;
import static org.codecraftlabs.s3app.util.CommandLineUtil.AWS_REGION_LONG_OPT;
import static org.codecraftlabs.s3app.util.CommandLineUtil.S3_BUCKET_NAME_LONG_OPT;
import static org.codecraftlabs.s3app.util.CommandLineUtil.S3_SERVICE_LONG_OPT;

public class AWSServiceExecutor {
    private static final Logger logger = LogManager.getLogger(AWSServiceExecutor.class);

    // todo: break the if-else into separate methods
    public static void execute(Map<String, String> args) throws AWSException {
        var serviceName = args.getOrDefault(S3_SERVICE_LONG_OPT, "");
        var awsRegion = args.getOrDefault(AWS_REGION_LONG_OPT, "");
        var bucketName = args.getOrDefault(S3_BUCKET_NAME_LONG_OPT, "");

        Optional<CommandLineS3Service> operation = CommandLineS3Service.findByCode(serviceName);
        if (operation.isPresent() && CREATE_BUCKET == operation.get()) {
            runCreateBucketService(awsRegion, bucketName);
        } else if (operation.isPresent() && DELETE_BUCKET == operation.get()) {
            runDeleteBucketService(awsRegion, bucketName);
        } else if (operation.isPresent() && LIST_BUCKET == operation.get()) {
            runListBucketService(awsRegion);
        } else if (operation.isPresent() && LIST_OBJECTS == operation.get()) {
            runListObjectsService(awsRegion, bucketName);
        } else {
            logger.warn("No action performed");
        }
    }

    private static void runListBucketService(@Nonnull String awsRegion) throws AWSException {
        var region = AWSRegion.findByCode(awsRegion);
        var service = new S3BucketListService();
        var buckets = service.buckets(region.orElseThrow());
        buckets.forEach(logger::info);
    }

    private static void runCreateBucketService(@Nonnull String awsRegion, @Nonnull String bucketName) throws AWSException {
        var region = AWSRegion.findByCode(awsRegion);
        var bucket = new S3Bucket(bucketName, region.orElseThrow());
        var service = new S3BucketCreateService();
        service.create(bucket);
    }

    private static void runDeleteBucketService(@Nonnull String awsRegion, @Nonnull String bucketName) throws AWSException {
        var region = AWSRegion.findByCode(awsRegion);
        var bucket = new S3Bucket(bucketName, region.orElseThrow());
        var service = new S3BucketDeleteService();
        service.remove(bucket);
    }

    private static void runListObjectsService(@Nonnull String awsRegion, @Nonnull String bucketName) throws AWSException {
        var region = AWSRegion.findByCode(awsRegion);
        var service = new S3ObjectListService();
        var bucket = new S3Bucket(bucketName, region.orElseThrow());
        var results = service.list(bucket);
        results.forEach(logger::info);
    }
}
