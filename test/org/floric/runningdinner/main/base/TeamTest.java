package org.floric.runningdinner.main.base;

import org.floric.runningdinner.main.core.Person;
import org.floric.runningdinner.main.core.Team;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;

import static org.junit.Assert.assertEquals;

/**
 * Created by florian on 28.02.2016.
 */
public class TeamTest {

    private Team t;
    private Person pA = new Person("ABC", "DEF");
    private Person pB = new Person("GHI", "JKL");

    @Before
    public void setUp() throws Exception {
        t = new Team(pA, pB);
    }


    @Test
    public void testGetPersonA() throws Exception {
        assertEquals(pA, t.getPersonA());
    }

    @Test
    public void testGetPersonB() throws Exception {
        assertEquals(pB, t.getPersonB());
    }

    @Test
    public void testGetLocation() throws Exception {
        assertEquals(new Point2D.Double(0, 0), t.getLocation());
    }

    @Test
    public void testSetLocation() throws Exception {
        Point2D newLocation = new Point2D.Double(1.0, 2.0);
        t.setLocation(newLocation);
        assertEquals(newLocation, t.getLocation());
    }

    @Test
    public void testGetGroupIndex() throws Exception {
        assertEquals(0, t.getGroupIndex());
    }

}