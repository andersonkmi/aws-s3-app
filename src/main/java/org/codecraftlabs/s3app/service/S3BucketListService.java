package org.codecraftlabs.s3app.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.data.AWSRegion;
import org.codecraftlabs.s3app.data.S3Bucket;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.stream.Collectors;

import static org.codecraftlabs.s3app.data.AWSRegion.US_EAST_1;
import static org.codecraftlabs.s3app.util.AWSRegionMapper.awsRegion;

public class S3BucketListService {
    private static final Logger logger = LogManager.getLogger(S3BucketListService.class);

    public Set<S3Bucket> buckets() throws AWSException {
        return buckets(US_EAST_1);
    }

    public Set<S3Bucket> buckets(@Nonnull final AWSRegion region) throws AWSException {
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
