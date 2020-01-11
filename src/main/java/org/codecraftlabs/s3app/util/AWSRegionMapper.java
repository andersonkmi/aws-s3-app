package org.codecraftlabs.s3app.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.data.AWSRegion;
import software.amazon.awssdk.regions.Region;

import javax.annotation.Nonnull;

public class AWSRegionMapper {
    private static final Logger logger = LogManager.getLogger(AWSRegionMapper.class);

    public static Region awsRegion(@Nonnull AWSRegion from) {
        logger.debug(String.format("Mapping region '%s - %s'", from.code(), from.description()));
        var to = Region.of(from.code());
        if (to == null) {
            logger.info("Could not map to an existing AWS region. Defaulting to US_EAST_1");
            return Region.US_EAST_1;
        }
        return to;
    }
}
