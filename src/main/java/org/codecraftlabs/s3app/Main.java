package org.codecraftlabs.s3app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.s3app.service.AWSException;
import org.codecraftlabs.s3app.service.AWSServiceExecutor;
import org.codecraftlabs.s3app.validator.AppArgsValidator;
import org.codecraftlabs.s3app.util.CommandLineException;
import org.codecraftlabs.s3app.util.CommandLineUtil;
import org.codecraftlabs.s3app.validator.InvalidArgumentException;
import org.codecraftlabs.s3app.validator.RegionOptionValidationPolicy;
import org.codecraftlabs.s3app.validator.ServiceOptionValidationPolicy;

import static org.codecraftlabs.s3app.util.CommandLineUtil.help;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting app");
        try {
            var commandLineUtil = new CommandLineUtil();
            var arguments = commandLineUtil.parse(args);

            var cliValidator = new AppArgsValidator();
            cliValidator.add(new RegionOptionValidationPolicy());
            cliValidator.add(new ServiceOptionValidationPolicy());
            cliValidator.validate(arguments);

            var serviceExecutor = new AWSServiceExecutor();
            serviceExecutor.execute(arguments);

            logger.info("App finished");
        } catch (AWSException exception) {
            logger.error(exception.getMessage(), exception);
        } catch (InvalidArgumentException | IllegalArgumentException | CommandLineException exception) {
            logger.error("Failed to parse command line options", exception);
            help();
        }
    }
}
