package org.example;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.sentry.SendCachedEnvelopeFireAndForgetIntegration;
import io.sentry.SendFireAndForgetEnvelopeSender;
import io.sentry.Sentry;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        Sentry.init(options -> {
            options.setEnableExternalConfiguration(true);
            options.setCacheDirPath("/Users/simon/Projects/my-log4j2/disk/path");
            options.addIntegration(
                new SendCachedEnvelopeFireAndForgetIntegration(
                new SendFireAndForgetEnvelopeSender(options::getCacheDirPath)
                )
            );
            options.setConnectionTimeoutMillis(10000);
            options.setReadTimeoutMillis(10000);
        });

        System.out.println("Hello and welcome!");
        logger.debug("Debug message");
        logger.info("Info message");
        logger.warn("Warn message");
        logger.error("Error message");

        for (int i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
            logger.info("i = " + i);
        }

        try {
            throw new Exception("This is a test.");
        } catch (Exception e) {
            Sentry.captureException(e);
        }

        int example = 1 / 0;

    }
}