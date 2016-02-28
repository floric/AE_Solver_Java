package org.floric.runningdinner.main.core;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by florian on 28.02.2016.
 */
public class LoggerTest {

    @Test
    public void testLog() throws Exception {
        Logger.Log(Logger.LOG_VERBOSITY.INFO, "Logtest");
    }
}