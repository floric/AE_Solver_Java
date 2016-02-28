package org.floric.runningdinner.main.base;

import javafx.geometry.Point2D;
import org.floric.runningdinner.main.core.Core;
import org.floric.runningdinner.util.DataGenerator;
import org.floric.runningdinner.util.Statistics;
import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by florian on 28.02.2016.
 */
public class TeamGroupTest {

    private TeamGroup t;
    private DataGenerator dg;

    @Before
    public void setUp() throws Exception {
        t = new TeamGroup();
        dg = Core.getInstance().getDataGenerator();
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
            teams.add(dg.getRandomTeam());
        }

        t.setTeams(teams);

        assertEquals(teams, t.getTeams());
    }

    @Test
    public void testSetTeams() throws Exception {
        ArrayList<Team> teams =  new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            teams.add(dg.getRandomTeam());
        }

        t.setTeams(teams);

        assertEquals(teams, t.getTeams());
    }

    @Test
    public void testAddTeam() throws Exception {
        ArrayList<Team> teams =  new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            Team newTeam = dg.getRandomTeam();
            t.addTeam(newTeam);
            assertEquals(newTeam, t.getTeams().get(t.getTeams().size() - 1));
        }

        try {
            t.addTeam(null);
            assert(false);
        } catch(IllegalArgumentException ex) {

        }
    }

    @Test
    public void testGetTotalDistanceToCenter() throws Exception {
        assertEquals(0.0, t.getTotalDistanceToCenter(Point2D.ZERO), 0.01);
        Team team = dg.getRandomTeam();

        Point2D newLoc = new Point2D(10.0, 10.0);
        Point2D center = new Point2D(50.0, 100.0);
        team.setLocation(newLoc);
        t.addTeam(team);

        assertEquals(newLoc.distance(center), t.getTotalDistanceToCenter(center), 0.01);
    }
}