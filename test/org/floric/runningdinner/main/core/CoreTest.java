package org.floric.runningdinner.main.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by florian on 28.02.2016.
 */
public class CoreTest {

    Core c = null;

    @Before
    public void setUp() throws Exception {
        c = Core.getInstance();
    }

    @Test
    public void testGetInstance() throws Exception {
        assertEquals(c, Core.getInstance());
    }

    @Test
    public void testGetDataGenerator() throws Exception {
        assertNotEquals(null, c.getDataGenerator());
    }


}