package org.codecraftlabs.s3app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.service.AWSException;
import org.codecraftlabs.s3app.util.CommandLineException;
import org.codecraftlabs.s3app.util.CommandLineUtil;
import org.codecraftlabs.s3app.util.InvalidArgumentException;

import static org.codecraftlabs.s3app.service.AWSServiceExecutor.execute;
import static org.codecraftlabs.s3app.util.CommandLineArgsValidator.validateCommandLineArgs;
import static org.codecraftlabs.s3app.util.CommandLineUtil.help;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final int ERROR_RETURN_CODE = 1;

    public static void main(String[] args) {
        try {
            var cmdLineUtil = new CommandLineUtil();
            cmdLineUtil.parse(args);

            var arguments = cmdLineUtil.options();
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
}
