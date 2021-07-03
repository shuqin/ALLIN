package zzz.study.foundations.loggings;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleLoggingDemo {

    public static void method() {
        Logger logger = Logger.getLogger("log in method()");
        logger.log(Level.WARNING, "it should be...");
    }

    public static void main(String[] args) {
        method();
        Logger logger = Logger.getLogger("SimpleLoggingDemo");
        logger.log(Level.INFO, "test of logging system.");
    }

}
