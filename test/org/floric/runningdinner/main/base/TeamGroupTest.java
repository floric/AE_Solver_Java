package org.floric.runningdinner.main.base;

import org.floric.runningdinner.main.core.Core;
import org.floric.runningdinner.util.DataGenerator;
import org.floric.runningdinner.util.Statistics;
import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by florian on 28.02.2016.
 */
public class TeamGroupTest {

    private TeamGroup t;

    @Before
    public void setUp() throws Exception {
        t = new TeamGroup();
    }

    @Test
    public void testGetGroupIndex() throws Exception {
        Core c = Core.getInstance();
        c.addTeamGroup(t);

        assertEquals(0, t.getGroupIndex());

        TeamGroup t2 = new TeamGroup();
        try {
            t2.getGroupIndex();
            assert(false);
        } catch (IllegalStateException ex) {

        }
    }

    @Test
    public void testGetTeams() throws Exception {
        ArrayList<Team> teams =  new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            teams.add(DataGenerator.getRandomTeam());
        }

        t.setTeams(teams);

        assertEquals(teams, t.getTeams());
    }

    @Test
    public void testSetTeams() throws Exception {
        ArrayList<Team> teams =  new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            teams.add(DataGenerator.getRandomTeam());
        }

        t.setTeams(teams);

        assertEquals(teams, t.getTeams());
    }

    @Test
    public void testAddTeam() throws Exception {
        ArrayList<Team> teams =  new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            Team newTeam = DataGenerator.getRandomTeam();
            t.addTeam(newTeam);
            assertEquals(newTeam, t.getTeams().get(t.getTeams().size() - 1));
        }

        try {
            t.addTeam(null);
            assert(false);
        } catch(IllegalArgumentException ex) {

        }
    }
}