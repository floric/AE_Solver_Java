package org.floric.runningdinner.main.core;

import org.floric.runningdinner.util.ILogOutput;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/** Class for logging.
 *
 * Created by Florian on 24.02.2016.
 */
public class Logger {

    private static List<ILogOutput> observers = new LinkedList<>();

    public static void Log(LOG_VERBOSITY state, String message) {
        LocalDateTime currentTime = LocalDateTime.now();

        System.out.println("[" + state.toString() + " | " + currentTime.format(DateTimeFormatter.ofPattern("dd.MM.y HH:mm:ss")) + "] " + message);

        switch (state) {
            case INFO:


                break;
            case MAIN:
                displayLog(message, state);

                break;
            case ERROR:
                displayLog(message, state);

                break;
            default:

        }
    }

    public static void addObserver(ILogOutput obj) {
        observers.add(obj);
    }

    public static void displayLog(String message, LOG_VERBOSITY verbosity) {
        observers.forEach(iLogOutput -> iLogOutput.updateLogOutput(message, verbosity));
    }

    public enum LOG_VERBOSITY {
        INFO, MAIN, ERROR
    }
}
