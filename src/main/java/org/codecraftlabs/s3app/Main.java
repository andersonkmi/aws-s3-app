package org.codecraftlabs.s3app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.data.AWSRegion;
import org.codecraftlabs.s3app.data.S3Bucket;
import org.codecraftlabs.s3app.service.AWSException;
import org.codecraftlabs.s3app.service.S3BucketCreateService;
import org.codecraftlabs.s3app.service.S3BucketDeleteService;
import org.codecraftlabs.s3app.service.S3BucketListService;
import org.codecraftlabs.s3app.util.CommandLineException;
import org.codecraftlabs.s3app.util.CommandLineUtil;
import org.codecraftlabs.s3app.util.S3Operation;

import java.util.Map;
import java.util.Set;

import static org.codecraftlabs.s3app.util.CommandLineUtil.AWS_REGION_LONG_OPT;
import static org.codecraftlabs.s3app.util.CommandLineUtil.S3_BUCKET_NAME_LONG_OPT;
import static org.codecraftlabs.s3app.util.CommandLineUtil.S3_SERVICE_LONG_OPT;
import static org.codecraftlabs.s3app.util.CommandLineUtil.help;
import static org.codecraftlabs.s3app.util.S3Operation.CREATE_BUCKET;
import static org.codecraftlabs.s3app.util.S3Operation.DELETE_BUCKET;
import static org.codecraftlabs.s3app.util.S3Operation.LIST_BUCKET;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final int ERROR_RETURN_CODE = 1;

    public static void main(String[] args) {
        logger.info("Starting the app");

        try {
            CommandLineUtil cmdLineUtil = new CommandLineUtil();
            cmdLineUtil.parse(args);
            execute(cmdLineUtil.options());
            logger.info("App finished OK!");
        } catch (AWSException exception) {
            logger.error(exception.getMessage(), exception);
            System.exit(ERROR_RETURN_CODE);
        } catch (IllegalArgumentException | CommandLineException exception) {
            logger.error("Failed to parse command line options", exception);
            help();
            System.exit(ERROR_RETURN_CODE);
        }
    }

    private static void execute(Map<String, String> args) throws AWSException {
        String serviceName = args.getOrDefault(S3_SERVICE_LONG_OPT, "");
        String awsRegion = args.getOrDefault(AWS_REGION_LONG_OPT, "");
        String bucketName = args.getOrDefault(S3_BUCKET_NAME_LONG_OPT, "");

        S3Operation operation = S3Operation.findByCode(serviceName);
        if (CREATE_BUCKET == operation) {
            AWSRegion region = AWSRegion.valueOf(awsRegion);
            S3Bucket bucket = new S3Bucket(bucketName, region);
            S3BucketCreateService service = new S3BucketCreateService();
            service.create(bucket);
        } else if (DELETE_BUCKET == operation) {
            AWSRegion region = AWSRegion.valueOf(awsRegion);
            S3Bucket bucket = new S3Bucket(bucketName, region);
            S3BucketDeleteService service = new S3BucketDeleteService();
            service.remove(bucket);
        } else if (LIST_BUCKET == operation) {
            AWSRegion region = AWSRegion.findByCode(awsRegion);
            if (region != null) {
                S3BucketListService service = new S3BucketListService();
                Set<S3Bucket> buckets = service.buckets(region);
                buckets.forEach(logger::info);
            }
        } else {
            logger.warn("No action performed");
        }
    }
}
