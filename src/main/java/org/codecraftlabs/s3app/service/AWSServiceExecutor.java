package org.codecraftlabs.s3app.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.data.AWSRegion;
import org.codecraftlabs.s3app.data.S3Bucket;
import org.codecraftlabs.s3app.util.AppArguments;
import org.codecraftlabs.s3app.util.CommandLineS3Service;

import javax.annotation.Nonnull;
import java.util.Optional;

import static org.codecraftlabs.s3app.util.AppArguments.BUCKET_OPTION;
import static org.codecraftlabs.s3app.util.AppArguments.REGION_OPTION;
import static org.codecraftlabs.s3app.util.AppArguments.SERVICE_OPTION;
import static org.codecraftlabs.s3app.util.CommandLineS3Service.CREATE_BUCKET;
import static org.codecraftlabs.s3app.util.CommandLineS3Service.DELETE_BUCKET;
import static org.codecraftlabs.s3app.util.CommandLineS3Service.LIST_BUCKETS;
import static org.codecraftlabs.s3app.util.CommandLineS3Service.LIST_OBJECTS;

public class AWSServiceExecutor {
    private static final Logger logger = LogManager.getLogger(AWSServiceExecutor.class);

    public static void execute(AppArguments args) throws AWSException {
        logger.info("Executing AWS service");
        var serviceName = args.option(SERVICE_OPTION);
        var awsRegion = args.option(REGION_OPTION);
        var bucketName = args.option(BUCKET_OPTION);

        Optional<CommandLineS3Service> operation = CommandLineS3Service.findByCode(serviceName);
        if (operation.isPresent() && CREATE_BUCKET == operation.get()) {
            runCreateBucketService(awsRegion, bucketName);
        } else if (operation.isPresent() && DELETE_BUCKET == operation.get()) {
            runDeleteBucketService(awsRegion, bucketName);
        } else if (operation.isPresent() && LIST_BUCKETS == operation.get()) {
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
        buckets.forEach(System.out::println);
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
        results.forEach(System.out::println);
    }
}
