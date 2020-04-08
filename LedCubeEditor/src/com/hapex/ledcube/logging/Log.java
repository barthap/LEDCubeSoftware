package com.hapex.ledcube.logging;

import static com.hapex.ledcube.logging.ConsoleLogger.*;

/**
 * Created by barthap on 10.12.2018.
 * No idea what to write here
 * *you know, no IDEA, IntelliJ IDEA xDDD
 */
public class Log {
    private static AbstractLogger getLoggerChain(){

        //AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);
        //AbstractLogger fileLogger = new FileLogger(AbstractLogger.DEBUG);
        AbstractLogger consoleLogger = new ConsoleLogger(AbstractLogger.DEBUG);

        //errorLogger.setNextLogger(fileLogger);
        //fileLogger.setNextLogger(consoleLogger);

        return consoleLogger;
    }

    private static AbstractLogger logger = getLoggerChain();

    public static void info(String... messages) {
        String msg = String.join(" ", messages);

        msg = ANSI_BLUE + "[INFO]  " + ANSI_RESET + msg;
        logger.logMessage(AbstractLogger.INFO, msg);
    }

    public static void error(String... messages) {
        String msg = String.join(" ", messages);

        msg = ANSI_RED + "[ERROR] " + msg + ANSI_RESET;
        logger.logMessage(AbstractLogger.DEBUG, msg);
    }

    public static void debug(String... messages) {
        String msg = String.join(" ", messages);

        msg = ANSI_GREEN + "[DEBUG] " + msg + ANSI_RESET;
        logger.logMessage(AbstractLogger.DEBUG, msg);
    }
}
