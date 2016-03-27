package org.floric.runningdinner.util;

import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;

/**
 * Created by florian on 28.02.2016.
 */
public class DataGeneratorTest {

    private static int TEAM_COUNT = 9;
    private static Point2D POINT_MIN = new Point2D.Double(-10, -10);
    private static Point2D POINT_MAX = new Point2D.Double(10, 10);
    private DataGenerator dg;

    @Before
    public void init() {
        dg = new DataGenerator(TEAM_COUNT, 1L);
    }

    @Test
    public void testGetRandomTeam() throws Exception {
        dg.getRandomTeam();
    }

    @Test
    public void testGetRandomPoint() throws Exception {
        for (int i = 0; i < 100; i++) {
            Point2D pt = dg.getRandomPoint(POINT_MIN, POINT_MAX);

            // check borders of min / max
            if (pt.getX() < POINT_MIN.getX()
                    || pt.getY() < POINT_MIN.getY()
                    || pt.getX() > POINT_MAX.getX()
                    || pt.getY() > POINT_MAX.getY()) {
                assert(false);
            }
        }
    }

    @Test
    public void testRecalculateData() throws Exception {

    }

    @Test
    public void testChangeSeed() throws Exception {

    }

    @Test
    public void testGetTeamCount() throws Exception {

    }

    @Test
    public void testSetTeamCount() throws Exception {

    }
}