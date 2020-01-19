package org.codecraftlabs.s3app.util;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

import static org.codecraftlabs.s3app.util.AppArguments.BUCKET_OPTION;
import static org.codecraftlabs.s3app.util.AppArguments.OBJECT_OPTION;
import static org.codecraftlabs.s3app.util.AppArguments.REGION_OPTION;
import static org.codecraftlabs.s3app.util.AppArguments.SERVICE_OPTION;

public class CommandLineUtil {
    private CommandLineParser commandLineParser;

    private static final Logger logger = LogManager.getLogger(CommandLineUtil.class);

    public static final String S3_SERVICE_OPT = "s";
    public static final String S3_BUCKET_NAME_OPT = "b";
    public static final String AWS_REGION_OPT = "r";
    public static final String OBJECT_NAME_OPT = "o";

    final private static Options cmdLineOpts = new Options().addRequiredOption(S3_SERVICE_OPT, SERVICE_OPTION, true, "Select which service")
            .addRequiredOption(AWS_REGION_OPT, REGION_OPTION, true, "AWS region to operate")
            .addOption(OBJECT_NAME_OPT, OBJECT_OPTION, true, "Object to upload")
            .addOption(S3_BUCKET_NAME_OPT, BUCKET_OPTION,true, "Bucket name");

    public CommandLineUtil() {
        commandLineParser = new DefaultParser();
    }

    public AppArguments parse(String[] args) throws CommandLineException {
        logger.info("Parsing command line arguments");

        final Map<String, String> options = new HashMap<>();

        try {
            var cmdLine = commandLineParser.parse(cmdLineOpts, args);
            options.put(SERVICE_OPTION, cmdLine.getOptionValue(S3_SERVICE_OPT));
            options.put(REGION_OPTION, cmdLine.getOptionValue(AWS_REGION_OPT));

            extractOptionalArgs(cmdLine, options);

            return new AppArguments(options);
        } catch (ParseException exception) {
            logger.error("Command line parse error", exception);
            throw new CommandLineException("Error when parsing command line options", exception);
        }
    }

    private void extractOptionalArgs(@Nonnull CommandLine line, @Nonnull Map<String, String> options) {
        if (line.hasOption(S3_BUCKET_NAME_OPT)) {
            options.put(BUCKET_OPTION, line.getOptionValue(S3_BUCKET_NAME_OPT));
        }

        if (line.hasOption(OBJECT_NAME_OPT)) {
            options.put(OBJECT_OPTION, line.getOptionValue(OBJECT_NAME_OPT));
        }
    }

    public static void help() {
        var header = "\nAWS S3 sandbox app\n";
        var footer = "\nThank you for using\n";
        new HelpFormatter().printHelp("java -jar aws-s3-app.jar", header, cmdLineOpts, footer, true);
    }
}
