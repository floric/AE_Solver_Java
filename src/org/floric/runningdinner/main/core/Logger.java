package org.floric.runningdinner.main.core;

/**
 * Created by Florian on 24.02.2016.
 */
public class Logger {

    public enum LOG_VERBOSITY {
        INFO, MAIN, IMPORTANT, ERROR;
    }

    // TODO Add current time
    public static void Log(LOG_VERBOSITY state, String message) {
        System.out.println("[" + state.toString() + " | Time]\t" + message);
    }
}
