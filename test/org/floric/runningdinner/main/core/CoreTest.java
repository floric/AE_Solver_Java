package org.floric.runningdinner.main.core;

import org.floric.runningdinner.main.base.TeamGroup;
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

    @Test
    public void testAddTeamGroup() throws Exception {
        TeamGroup t = new TeamGroup();
        c.addTeamGroup(t);
    }

    @Test
    public void testGetTeamsGroups() throws Exception {
        TeamGroup t = new TeamGroup();
        c.addTeamGroup(t);
        assertEquals(t, c.getTeamsGroups().get(1));
    }

    @Test
    public void testGetTeamGroupByIndex() throws Exception {
        TeamGroup t = new TeamGroup();
        c.addTeamGroup(t);
        assertEquals(t, c.getTeamGroupByIndex(2));

        try {
            c.getTeamGroupByIndex(-1);
            assert(false);
        } catch (IndexOutOfBoundsException ex) {

        }

        try {
            c.getTeamGroupByIndex(3);
            assert(false);
        } catch (IndexOutOfBoundsException ex) {

        }
    }
}