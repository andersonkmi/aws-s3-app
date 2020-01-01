package org.codecraftlabs.s3app.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.data.AwsRegion;
import software.amazon.awssdk.regions.Region;

public class AwsRegionMapper {
    private static final Logger logger = LogManager.getLogger(AwsRegionMapper.class);

    public static Region awsRegion(AwsRegion from) {
        Region to = Region.of(from.regionCode());
        if (to == null) {
            logger.info("Could not map to an existing AWS region. Defaulting to US_EAST_1");
            return Region.US_EAST_1;
        }
        return to;
    }
}
