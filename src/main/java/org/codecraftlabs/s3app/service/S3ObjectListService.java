package org.codecraftlabs.s3app.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.data.AWSRegion;
import org.codecraftlabs.s3app.data.S3Bucket;
import org.codecraftlabs.s3app.data.S3Object;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.codecraftlabs.s3app.util.AWSRegionMapper.awsRegion;

public class S3ObjectListService {
    private static final Logger logger = LogManager.getLogger(S3ObjectListService.class);

    public Set<S3Object> list(@Nonnull final AWSRegion region, @Nonnull final S3Bucket bucket) throws AWSException {
        Set<S3Object> result = new HashSet<>();
        logger.info(String.format("Listing all objects from bucket '%s'", bucket.name()));
        var s3Client = S3Client.builder().region(awsRegion(region)).build();
        var request = ListObjectsRequest.builder().bucket(bucket.name()).build();
        var s3Objects = s3Client.listObjects(request);
        List<software.amazon.awssdk.services.s3.model.S3Object> items = s3Objects.contents();
        return result;
    }
}
