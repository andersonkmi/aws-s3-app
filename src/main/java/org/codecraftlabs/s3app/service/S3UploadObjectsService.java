package org.codecraftlabs.s3app.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.data.AWSRegion;
import org.codecraftlabs.s3app.data.S3Bucket;

import javax.annotation.Nonnull;

class S3UploadObjectsService {
    private static final Logger logger = LogManager.getLogger(S3UploadObjectsService.class);

    public void uploadObjects(@Nonnull S3Bucket bucket, @Nonnull final AWSRegion region) {

    }
}
