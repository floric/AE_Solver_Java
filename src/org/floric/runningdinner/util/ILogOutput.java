package org.floric.runningdinner.util;

import org.floric.runningdinner.main.core.Logger;

/**
 * Created by florian on 25.03.2016.
 */
public interface ILogOutput {
    void updateLogOutput(String text, Logger.LOG_VERBOSITY verbosity);
}
