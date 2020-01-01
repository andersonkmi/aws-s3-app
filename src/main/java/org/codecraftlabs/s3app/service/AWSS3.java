package org.codecraftlabs.s3app.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.data.AWSRegion;
import org.codecraftlabs.s3app.data.S3Bucket;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketResponse;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.stream.Collectors;

import static org.codecraftlabs.s3app.data.AWSRegion.US_EAST_1;
import static org.codecraftlabs.s3app.util.AWSRegionMapper.awsRegion;

public final class AWSS3 {
    private static final Logger logger = LogManager.getLogger(AWSS3.class);

    private AWSS3() {
        // Disabled default constructor
    }

    public static void create(@Nonnull final S3Bucket bucket) throws AWSException {
        logger.info(String.format("Creating a new S3 bucket: '%s'", bucket.name()));
        try {
            Region selectedRegion = awsRegion(bucket.region());
            S3Client s3Client = S3Client.builder().region(selectedRegion).build();
            CreateBucketRequest request = CreateBucketRequest
                    .builder()
                    .bucket(bucket.name())
                    .build();
            s3Client.createBucket(request);
            logger.info(String.format("S3 bucket '%s' created!", bucket.name()));
        } catch (AwsServiceException | SdkClientException exception) {
            logger.warn("Error when creating a new bucket", exception);
            throw new AWSException("Error when deleting a new bucket", exception);
        }
    }

    public static void remove(@Nonnull final S3Bucket bucket) throws AWSException {
        logger.info(String.format("Removing the bucket: '%s'", bucket.name()));
        try {
            DeleteBucketRequest request = DeleteBucketRequest.builder().bucket(bucket.name()).build();
            Region selectedRegion = awsRegion(bucket.region());
            S3Client s3Client = S3Client.builder().region(selectedRegion).build();
            s3Client.deleteBucket(request);
            logger.info(String.format("S3 bucket '%s' deleted!", bucket.name()));
        } catch (AwsServiceException | SdkClientException exception) {
            logger.warn("Error when deleting a bucket", exception);
            throw new AWSException("Error when deleting a bucket", exception);
        }
    }

    public static Set<S3Bucket> buckets() throws AWSException {
        return buckets(US_EAST_1);
    }

    public static Set<S3Bucket> buckets(@Nonnull final AWSRegion region) throws AWSException {
        try {
            logger.info(String.format("Listing all buckets from region '%s'", region.code()));
            S3Client s3Client = S3Client.builder().region(awsRegion(region)).build();
            ListBucketsRequest request = ListBucketsRequest.builder().build();
            ListBucketsResponse response = s3Client.listBuckets(request);
            return response.buckets().stream().map(bucket -> new S3Bucket(bucket.name(), bucket.creationDate())).collect(Collectors.toSet());
        } catch (AwsServiceException | SdkClientException exception) {
            logger.warn("Error when listing buckets", exception);
            throw new AWSException("Error when listing buckets", exception);
        }
    }
}
