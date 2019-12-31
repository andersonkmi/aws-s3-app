package org.codecraftlabs.s3app.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.data.S3Bucket;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

import java.util.Set;
import java.util.stream.Collectors;

public class AwsS3 {
    private static final Logger logger = LogManager.getLogger(AwsS3.class);

    public void create(final S3Bucket bucket) throws AWSServiceException {

    }

    public void remove(final S3Bucket bucket) throws AWSServiceException {

    }

    public Set<S3Bucket> listAllBuckets()throws AWSServiceException {
        try {
            logger.info("Listing all buckets");
            S3Client s3Client = S3Client.builder().region(Region.US_EAST_1).build();
            ListBucketsRequest request = ListBucketsRequest.builder().build();
            ListBucketsResponse response = s3Client.listBuckets(request);
            return response.buckets().stream().map(bucket -> new S3Bucket(bucket.name(), bucket.creationDate())).collect(Collectors.toSet());
        } catch (AwsServiceException | SdkClientException exception) {
            logger.warn("Error when listing buckets", exception);
            throw new AWSServiceException("Error when listing buckets", exception);
        }
    }
}
