package org.codecraftlabs.s3app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.data.AWSRegion;
import org.codecraftlabs.s3app.data.S3Bucket;
import org.codecraftlabs.s3app.service.AWSException;
import org.codecraftlabs.s3app.service.S3BucketCreateService;
import org.codecraftlabs.s3app.service.S3BucketDeleteService;
import org.codecraftlabs.s3app.service.S3BucketListService;
import org.codecraftlabs.s3app.service.S3ObjectListService;
import org.codecraftlabs.s3app.util.CommandLineException;
import org.codecraftlabs.s3app.util.CommandLineUtil;
import org.codecraftlabs.s3app.util.InvalidArgumentException;
import org.codecraftlabs.s3app.util.CommandLineS3Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.codecraftlabs.s3app.util.CommandLineS3Service.LIST_OBJECTS;
import static org.codecraftlabs.s3app.util.CommandLineUtil.AWS_REGION_LONG_OPT;
import static org.codecraftlabs.s3app.util.CommandLineUtil.S3_BUCKET_NAME_LONG_OPT;
import static org.codecraftlabs.s3app.util.CommandLineUtil.S3_SERVICE_LONG_OPT;
import static org.codecraftlabs.s3app.util.CommandLineUtil.help;
import static org.codecraftlabs.s3app.util.CommandLineS3Service.CREATE_BUCKET;
import static org.codecraftlabs.s3app.util.CommandLineS3Service.DELETE_BUCKET;
import static org.codecraftlabs.s3app.util.CommandLineS3Service.LIST_BUCKET;
import static org.codecraftlabs.s3app.util.CommandLineArgsValidator.validateCommandLineArgs;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final int ERROR_RETURN_CODE = 1;

    public static void main(String[] args) {
        try {
            CommandLineUtil cmdLineUtil = new CommandLineUtil();
            cmdLineUtil.parse(args);

            Map<String, String> arguments = cmdLineUtil.options();
            validateCommandLineArgs(arguments);
            execute(arguments);
        } catch (AWSException exception) {
            logger.error(exception.getMessage(), exception);
            System.exit(ERROR_RETURN_CODE);
        } catch (InvalidArgumentException | IllegalArgumentException | CommandLineException exception) {
            logger.error("Failed to parse command line options", exception);
            help();
            System.exit(ERROR_RETURN_CODE);
        }
    }

    private static void execute(Map<String, String> args) throws AWSException {
        String serviceName = args.getOrDefault(S3_SERVICE_LONG_OPT, "");
        String awsRegion = args.getOrDefault(AWS_REGION_LONG_OPT, "");
        String bucketName = args.getOrDefault(S3_BUCKET_NAME_LONG_OPT, "");

        Optional<CommandLineS3Service> operation = CommandLineS3Service.findByCode(serviceName);
        if (operation.isPresent() && CREATE_BUCKET == operation.get()) {
            Optional<AWSRegion> region = AWSRegion.findByCode(awsRegion);
            S3Bucket bucket = new S3Bucket(bucketName, region.orElseThrow());
            S3BucketCreateService service = new S3BucketCreateService();
            service.create(bucket);
        } else if (operation.isPresent() && DELETE_BUCKET == operation.get()) {
            Optional<AWSRegion> region = AWSRegion.findByCode(awsRegion);
            S3Bucket bucket = new S3Bucket(bucketName, region.orElseThrow());
            S3BucketDeleteService service = new S3BucketDeleteService();
            service.remove(bucket);
        } else if (operation.isPresent() && LIST_BUCKET == operation.get()) {
            Optional<AWSRegion> region = AWSRegion.findByCode(awsRegion);
            S3BucketListService service = new S3BucketListService();
            Set<S3Bucket> buckets = service.buckets(region.orElseThrow());
            buckets.forEach(logger::info);
        } else if (operation.isPresent() && LIST_OBJECTS == operation.get()) {
            var region = AWSRegion.findByCode(awsRegion);
            var service = new S3ObjectListService();
            var bucket = new S3Bucket(bucketName, region.orElseThrow());
            var results = service.list(bucket);
            results.forEach(logger::info);
        } else {
            logger.warn("No action performed");
        }
    }
}
