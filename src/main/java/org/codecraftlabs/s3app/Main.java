package org.codecraftlabs.s3app;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.data.S3Bucket;
import org.codecraftlabs.s3app.service.AWSException;
import org.codecraftlabs.s3app.service.S3BucketListService;

import java.util.Set;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final String[] SERVICE_OPTIONS = {"s", "service"};
    private static final String[] BUCKET_OPTIONS = {"b", "bucket"};

    public static void main(String[] args) {
        logger.info("Starting the app");
        final Options cmdLineOpts = new Options();
        cmdLineOpts.addOption(SERVICE_OPTIONS[0], SERVICE_OPTIONS[1], true, "Select which service")
                .addOption(BUCKET_OPTIONS[0], BUCKET_OPTIONS[1], true, "Bucket name");

        CommandLineParser cmdLineParser = new DefaultParser();

        try {
            CommandLine cmdLine = cmdLineParser.parse(cmdLineOpts, args);

            S3BucketListService service = new S3BucketListService();
            Set<S3Bucket> buckets = service.buckets();
            buckets.forEach(logger::info);
            logger.info("App finished OK!");
        } catch (AWSException exception) {
            logger.error(exception.getMessage(), exception);
        } catch (ParseException exception) {
            logger.error("Failed to parse command line options", exception);
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("ant", cmdLineOpts, true);
        }
    }
}
