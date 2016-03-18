package org.floric.runningdinner.main.core;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Class for logging.
 *
 * Created by Florian on 24.02.2016.
 */
public class Logger {

    private static Label statusLabel;

    public static void Log(LOG_VERBOSITY state, String message) {
        LocalDateTime currentTime = LocalDateTime.now();

        System.out.println("[" + state.toString() + " | " + currentTime.format(DateTimeFormatter.ofPattern("dd.MM.y HH:mm:ss")) + "] " + message);

        if (statusLabel != null) {
            switch (state) {
                case INFO:


                    break;
                case MAIN:
                    statusLabel.setText(message);
                    statusLabel.setTextFill(Color.BLACK);

                    break;
                case ERROR:
                    statusLabel.setText(message);
                    statusLabel.setTextFill(Color.RED);

                    break;
                default:

            }
        }
    }

    public static void setStatusLabel(Label l) {
        statusLabel = l;
    }

    public enum LOG_VERBOSITY {
        INFO, MAIN, ERROR
    }
}
