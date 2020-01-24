package org.codecraftlabs.s3app.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.data.S3Bucket;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

import javax.annotation.Nonnull;

import static org.codecraftlabs.s3app.util.AWSRegionMapper.awsRegion;

class S3BucketCreateService {
    private static final Logger logger = LogManager.getLogger(S3BucketCreateService.class);

    public void create(@Nonnull final S3Bucket bucket) throws AWSException {
        logger.info(String.format("Creating a new S3 bucket: '%s'", bucket.name()));
        try {
            var selectedRegion = awsRegion(bucket.region());
            var s3Client = S3Client.builder().region(selectedRegion).build();
            var request = CreateBucketRequest.builder().bucket(bucket.name()).build();
            s3Client.createBucket(request);
            logger.info(String.format("S3 bucket '%s' created!", bucket.name()));
        } catch (AwsServiceException | SdkClientException exception) {
            String errorMessage = "Error when creating a new bucket";
            logger.warn(errorMessage, exception);
            throw new AWSException(errorMessage, exception);
        }
    }
}
