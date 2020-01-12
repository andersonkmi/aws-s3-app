package org.codecraftlabs.s3app.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.data.S3Bucket;
import org.codecraftlabs.s3app.data.S3Object;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.stream.Collectors;

import static org.codecraftlabs.s3app.util.AWSRegionMapper.awsRegion;

class S3ObjectListService {
    private static final Logger logger = LogManager.getLogger(S3ObjectListService.class);

    public Set<S3Object> list(@Nonnull final S3Bucket bucket) throws AWSException {
        try {
            logger.info(String.format("Listing all objects from bucket '%s'", bucket.name()));
            var s3Client = S3Client.builder().region(awsRegion(bucket.region())).build();
            var request = ListObjectsRequest.builder().bucket(bucket.name()).build();
            var s3Objects = s3Client.listObjects(request);
            var items = s3Objects.contents();
            return items.stream().map(item -> new S3Object(item.key(), item.eTag(), item.lastModified())).collect(Collectors.toSet());
        } catch (AwsServiceException | SdkClientException exception) {
            logger.warn("Error when listing objects", exception);
            throw new AWSException("Error when listing objects", exception);
        }
    }
}
