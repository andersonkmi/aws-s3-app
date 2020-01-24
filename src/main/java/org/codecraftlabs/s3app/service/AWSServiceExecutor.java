package org.codecraftlabs.s3app.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.codecraftlabs.s3app.data.AWSRegion;
import org.codecraftlabs.s3app.data.S3Bucket;
import org.codecraftlabs.s3app.data.S3Object;
import org.codecraftlabs.s3app.util.AppArguments;
import org.codecraftlabs.s3app.util.CommandLineS3Service;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;

import static org.codecraftlabs.s3app.util.AppArguments.BUCKET_OPTION;
import static org.codecraftlabs.s3app.util.AppArguments.KEY_OPTION;
import static org.codecraftlabs.s3app.util.AppArguments.OBJECT_OPTION;
import static org.codecraftlabs.s3app.util.AppArguments.REGION_OPTION;
import static org.codecraftlabs.s3app.util.AppArguments.SERVICE_OPTION;
import static org.codecraftlabs.s3app.util.CommandLineS3Service.CREATE_BUCKET;
import static org.codecraftlabs.s3app.util.CommandLineS3Service.DELETE_BUCKET;
import static org.codecraftlabs.s3app.util.CommandLineS3Service.DELETE_OBJECTS;
import static org.codecraftlabs.s3app.util.CommandLineS3Service.LIST_BUCKETS;
import static org.codecraftlabs.s3app.util.CommandLineS3Service.LIST_OBJECTS;
import static org.codecraftlabs.s3app.util.CommandLineS3Service.UPLOAD_OBJECTS;
import static org.codecraftlabs.s3app.util.CommandLineS3Service.findByCode;

public class AWSServiceExecutor {
    private static final Logger logger = LogManager.getLogger(AWSServiceExecutor.class);

    public void execute(AppArguments args) throws AWSException {
        logger.info(String.format("Executing AWS service: %s", args.option(SERVICE_OPTION)));
        var serviceName = args.option(SERVICE_OPTION);
        var awsRegion = args.option(REGION_OPTION);
        var bucketName = args.option(BUCKET_OPTION);
        var fileName = args.option(OBJECT_OPTION);
        var keyName = args.option(KEY_OPTION);

        Optional<CommandLineS3Service> operation = findByCode(serviceName);
        if (operation.isPresent() && CREATE_BUCKET == operation.get()) {
            runCreateBucketService(awsRegion, bucketName);
        } else if (operation.isPresent() && DELETE_BUCKET == operation.get()) {
            runDeleteBucketService(awsRegion, bucketName);
        } else if (operation.isPresent() && LIST_BUCKETS == operation.get()) {
            runListBucketService(awsRegion);
        } else if (operation.isPresent() && LIST_OBJECTS == operation.get()) {
            runListObjectsService(awsRegion, bucketName);
        } else if (operation.isPresent() && UPLOAD_OBJECTS == operation.get()) {
            runUploadObjectsService(awsRegion, bucketName, fileName, keyName);
        } else if (operation.isPresent() && DELETE_OBJECTS == operation.get()) {

        }
    }

    private void runListBucketService(@Nonnull String awsRegion) throws AWSException {
        var region = AWSRegion.findByCode(awsRegion);
        var service = new S3BucketListService();
        var buckets = service.buckets(region.orElseThrow());
        buckets.forEach(logger::info);
        buckets.forEach(System.out::println);
    }

    private void runCreateBucketService(@Nonnull String awsRegion, @Nonnull String bucketName) throws AWSException {
        var region = AWSRegion.findByCode(awsRegion);
        var bucket = new S3Bucket(bucketName, region.orElseThrow());
        var service = new S3BucketCreateService();
        service.create(bucket);
    }

    private void runDeleteBucketService(@Nonnull String awsRegion, @Nonnull String bucketName) throws AWSException {
        var region = AWSRegion.findByCode(awsRegion);
        var bucket = new S3Bucket(bucketName, region.orElseThrow());
        var service = new S3BucketDeleteService();
        service.remove(bucket);
    }

    private void runListObjectsService(@Nonnull String awsRegion, @Nonnull String bucketName) throws AWSException {
        var region = AWSRegion.findByCode(awsRegion);
        var service = new S3ObjectListService();
        var bucket = new S3Bucket(bucketName, region.orElseThrow());
        var results = service.list(bucket);
        results.forEach(logger::info);
        results.forEach(System.out::println);
    }

    private void runUploadObjectsService(@Nonnull String awsRegion, @Nonnull String bucketName, @Nonnull String fileName, @Nonnull String keyName) throws AWSException {
        var region = AWSRegion.findByCode(awsRegion);
        var bucket = new S3Bucket(bucketName, region.orElseThrow());
        var s3Objects = Map.of(fileName, new S3Object(keyName));
        var service = new S3UploadObjectsService();
        service.uploadObjects(bucket, s3Objects);
    }

    private void runDeleteObjectsService(@Nonnull String awsRegion, @Nonnull String bucketName, @NonNull String keyName) throws AWSException {
        var region = AWSRegion.findByCode(awsRegion);
        var bucket = new S3Bucket(bucketName, region.orElseThrow());
    }
}
