package org.floric.runningdinner.main.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Class for logging.
 *
 * Created by Florian on 24.02.2016.
 */
public class Logger {

    // TODO Add current time
    public static void Log(LOG_VERBOSITY state, String message) {
        LocalDateTime currentTime = LocalDateTime.now();

        System.out.println("[" + state.toString() + " | " + currentTime.format(DateTimeFormatter.ofPattern("dd.MM.y H:m:s"))  +"] " + message);
    }

    public enum LOG_VERBOSITY {
        INFO, MAIN, IMPORTANT, ERROR
    }
}
