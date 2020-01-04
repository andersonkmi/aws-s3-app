package org.codecraftlabs.s3app.util;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CommandLineUtil {
    private static final Logger logger = LogManager.getLogger(CommandLineUtil.class);

    public static final String S3_SERVICE_OPT = "s";
    public static final String S3_SERVICE_LONG_OPT = "service";
    public static final String S3_BUCKET_NAME_OPT = "b";
    public static final String S3_BUCKET_NAME_LONG_OPT = "bucket";
    public static final String AWS_REGION_OPT = "r";
    public static final String AWS_REGION_LONG_OPT = "region";

    final private Map<String, String> options = new HashMap<>();
    final private Options cmdLineOpts = new Options();

    public CommandLineUtil() {
        cmdLineOpts.addRequiredOption(S3_SERVICE_OPT, S3_SERVICE_LONG_OPT, true, "Select which service")
                .addRequiredOption(AWS_REGION_OPT, AWS_REGION_LONG_OPT, true, "AWS region to operate")
                .addOption(S3_BUCKET_NAME_OPT, S3_BUCKET_NAME_LONG_OPT,true, "Bucket name");
    }

    public void parseArgs(String[] args) throws CommandLineException {
        try {
            CommandLineParser cmdLineParser = new DefaultParser();
            CommandLine cmdLine = cmdLineParser.parse(cmdLineOpts, args);

            options.put(S3_SERVICE_LONG_OPT, cmdLine.getOptionValue(S3_SERVICE_OPT));
            options.put(AWS_REGION_LONG_OPT, cmdLine.getOptionValue(AWS_REGION_OPT));

            if (cmdLine.hasOption(S3_BUCKET_NAME_OPT)) {
                options.put(S3_BUCKET_NAME_LONG_OPT, cmdLine.getOptionValue(S3_BUCKET_NAME_OPT));
            }
        } catch (ParseException exception) {
            logger.error("Command line parse error", exception);
            throw new CommandLineException("Error when parsing command line options", exception);
        }
    }

    public String getOptionValue(String key) {
        return options.getOrDefault(key, "");
    }

    public void showHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        String header = "\nAWS S3 sandbox app\n";
        String footer = "\nThank you for using\n";
        helpFormatter.printHelp("java -jar aws-s3-app.jar", header, cmdLineOpts, footer, true);
    }
}
