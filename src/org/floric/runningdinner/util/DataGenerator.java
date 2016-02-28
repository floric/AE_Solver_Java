package org.floric.runningdinner.util;

import javafx.util.Pair;
import org.floric.runningdinner.main.base.Person;
import org.floric.runningdinner.main.base.Team;
import org.floric.runningdinner.main.core.Core;
import org.floric.runningdinner.main.core.Logger;

import java.util.Random;

/**
 * Created by florian on 20.02.2016.
 */
public class DataGenerator {

    private static int COORD_MULTIPLY = 10;
    private int teamCount = 0;
    private Pair<Double, Double>[] coords;
    private Random rand;

    public DataGenerator(int teamCount, long seed) {

        this.teamCount = teamCount;
        rand = new Random(seed);

        coords = getRandomCoordinates();


    }

    public static Team getRandomTeam() {
        Random tempRand = new Random(1L);
        return new Team(new Person("P" + String.valueOf(tempRand.nextInt(9999)), "N"), new Person("P" + String.valueOf(tempRand.nextInt(9999)), "N"));
    }

    public void recalculateData() {
        coords = getRandomCoordinates();
        Logger.Log(Logger.LOG_VERBOSITY.INFO, "Testdata new generated for " + teamCount + " teams");
    }

    private Pair<Double, Double>[] getRandomCoordinates() {
        Pair<Double, Double>[] coords = new Pair[getTeamCount()];

        // fill with random coordinates
        for (int i = 0; i < coords.length; i++) {
            coords[i] = new Pair<>(new Double(rand.nextDouble() * COORD_MULTIPLY), new Double(rand.nextDouble() * COORD_MULTIPLY));
        }

        return coords;
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

    public Pair<Double, Double>[] getCoords() {
        return coords;
    }
}
