package org.codecraftlabs.s3app.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.data.S3Bucket;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;

import javax.annotation.Nonnull;

import static org.codecraftlabs.s3app.util.AWSRegionMapper.awsRegion;

class S3BucketDeleteService {
    private static final Logger logger = LogManager.getLogger(S3BucketDeleteService.class);

    public void remove(@Nonnull final S3Bucket bucket) throws AWSException {
        logger.info(String.format("Removing the bucket: '%s'", bucket.name()));
        try {
            var request = DeleteBucketRequest.builder().bucket(bucket.name()).build();
            var selectedRegion = awsRegion(bucket.region());
            var s3Client = S3Client.builder().region(selectedRegion).build();
            s3Client.deleteBucket(request);
            logger.info(String.format("S3 bucket '%s' deleted!", bucket.name()));
        } catch (AwsServiceException | SdkClientException exception) {
            String errorMessage = "Error when deleting a bucket";
            logger.warn(errorMessage, exception);
            throw new AWSException(errorMessage, exception);
        }
    }
}
