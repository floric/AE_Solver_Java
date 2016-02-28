package org.floric.runningdinner.util;

import javafx.util.Pair;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by florian on 28.02.2016.
 */
public class MathUtilTest {

    @Test
    public void testGetDistanceBetween() throws Exception {
        assertEquals(MathUtil.getDistanceBetween(new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0)), 0.0, 0.01);
        assertEquals(MathUtil.getDistanceBetween(new Pair<>(0.0, 10.0), new Pair<>(0.0, 0.0)), 10.0, 0.01);
        assertEquals(MathUtil.getDistanceBetween(new Pair<>(0.0, 0.0), new Pair<>(0.0, 10.0)), 10.0, 0.01);
    }
}