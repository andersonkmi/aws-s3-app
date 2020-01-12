package org.codecraftlabs.s3app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.service.AWSException;
import org.codecraftlabs.s3app.util.CommandLineException;
import org.codecraftlabs.s3app.util.InvalidArgumentException;

import static java.lang.System.exit;
import static org.codecraftlabs.s3app.service.AWSServiceExecutor.execute;
import static org.codecraftlabs.s3app.util.CommandLineArgsValidator.validateCommandLineArgs;
import static org.codecraftlabs.s3app.util.CommandLineUtil.help;
import static org.codecraftlabs.s3app.util.CommandLineUtil.parse;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final int ERROR_RETURN_CODE = 1;

    public static void main(String[] args) {
        try {
            var arguments = parse(args);
            validateCommandLineArgs(arguments);
            execute(arguments);
        } catch (AWSException exception) {
            logger.error(exception.getMessage(), exception);
            exit(ERROR_RETURN_CODE);
        } catch (InvalidArgumentException | IllegalArgumentException | CommandLineException exception) {
            logger.error("Failed to parse command line options", exception);
            help();
            exit(ERROR_RETURN_CODE);
        }
    }
}
