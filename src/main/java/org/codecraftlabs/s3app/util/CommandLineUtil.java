package org.codecraftlabs.s3app.util;

import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static org.codecraftlabs.s3app.util.AppArguments.BUCKET_OPTION;
import static org.codecraftlabs.s3app.util.AppArguments.REGION_OPTION;
import static org.codecraftlabs.s3app.util.AppArguments.SERVICE_OPTION;

public class CommandLineUtil {
    private static final Logger logger = LogManager.getLogger(CommandLineUtil.class);

    public static final String S3_SERVICE_OPT = "s";
    public static final String S3_BUCKET_NAME_OPT = "b";
    public static final String AWS_REGION_OPT = "r";

    final private static Options cmdLineOpts = new Options().addRequiredOption(S3_SERVICE_OPT, SERVICE_OPTION, true, "Select which service")
            .addRequiredOption(AWS_REGION_OPT, REGION_OPTION, true, "AWS region to operate")
            .addOption(S3_BUCKET_NAME_OPT, BUCKET_OPTION,true, "Bucket name");

    public static AppArguments parse(String[] args) throws CommandLineException {
        final Map<String, String> options = new HashMap<>();
        try {
            var cmdLineParser = new DefaultParser();
            var cmdLine = cmdLineParser.parse(cmdLineOpts, args);

            options.put(SERVICE_OPTION, cmdLine.getOptionValue(S3_SERVICE_OPT));
            options.put(REGION_OPTION, cmdLine.getOptionValue(AWS_REGION_OPT));

            if (cmdLine.hasOption(S3_BUCKET_NAME_OPT)) {
                options.put(BUCKET_OPTION, cmdLine.getOptionValue(S3_BUCKET_NAME_OPT));
            }

            return new AppArguments(options);
        } catch (ParseException exception) {
            logger.error("Command line parse error", exception);
            throw new CommandLineException("Error when parsing command line options", exception);
        }
    }

    public static void help() {
        var header = "\nAWS S3 sandbox app\n";
        var footer = "\nThank you for using\n";
        new HelpFormatter().printHelp("java -jar aws-s3-app.jar", header, cmdLineOpts, footer, true);
    }
}
