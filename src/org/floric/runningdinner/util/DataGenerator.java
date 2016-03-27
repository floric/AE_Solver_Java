package org.floric.runningdinner.util;

import org.floric.runningdinner.main.core.Core;
import org.floric.runningdinner.main.core.Logger;
import org.floric.runningdinner.main.core.Person;
import org.floric.runningdinner.main.core.Team;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by florian on 20.02.2016.
 */
public class DataGenerator {

    private static int COORD_MULTIPLY = 10;
    private static Point2D COORD_MIN = new Point2D.Double(0.0, 0.0);
    private static Point2D COORD_MAX = new Point2D.Double(100.0, 100.0);
    private int teamCount = 0;
    private Random rand;


    public DataGenerator(int teamCount, long seed) {
        this.teamCount = teamCount;
        rand = new Random(seed);
    }

    public Team getRandomTeam() {
        Team t = new Team(new Person("P" + String.valueOf(rand.nextInt(9999)), "N"), new Person("P" + String.valueOf(rand.nextInt(9999)), "N"));
        t.setLocation(getRandomPoint());
        return t;
    }

    public Point2D getRandomPoint(Point2D min, Point2D max) {
        return new Point2D.Double(rand.nextDouble() * (max.getX() + min.getX()), rand.nextDouble() * (max.getY() + min.getY()));
    }

    public Point2D getRandomPoint() {
        return new Point2D.Double(rand.nextDouble() * (COORD_MAX.getX() + COORD_MIN.getX()), rand.nextDouble() * (COORD_MAX.getY() + COORD_MIN.getY()));
    }

    public void changeSeed(long seed) {
        rand.setSeed(seed);
    }

    public int getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(int count) {
        if (teamCount >= Core.TEAMS_MIN && teamCount <= Core.TEAMS_MAX && count % 3 == 0) {
            this.teamCount = count;
        } else {
            throw new IllegalArgumentException("Unsupported team count!");
        }
    }

    public ArrayList<Team> getRandomTeams() {
        ArrayList<Team> randomTeams = new ArrayList<>();

        for (int i = 0; i < teamCount; i++) {
            randomTeams.add(getRandomTeam());
        }

        Logger.Log(Logger.LOG_VERBOSITY.INFO, "Generated " + teamCount + " new random teams!");

        return randomTeams;
    }
}
