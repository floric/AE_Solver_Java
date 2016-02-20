package org.floric.runningdinner.util;

import javafx.util.Pair;

import java.util.Random;

/**
 * Created by florian on 20.02.2016.
 */
public class DataGenerator {

    private int teamCount = 0;
    private Pair<Double, Double>[] coords;
    private Random rand;

    private static int COORD_MULTIPLY = 10;

    public DataGenerator(int teamCount, long seed) {

        this.teamCount = teamCount;
        rand = new Random(seed);

        coords = getRandomCoordinates();


    }

    private Pair<Double, Double>[] getRandomCoordinates() {
        Pair<Double, Double>[] coords = new Pair[getTeamCount()];

        // fill with random coordinates
        for (int i = 0; i < coords.length; i++) {
            coords[i] = new Pair<>(new Double(rand.nextDouble() * COORD_MULTIPLY), new Double(rand.nextDouble() * COORD_MULTIPLY));
        }

        return coords;
    }


    public int getTeamCount() {
        return teamCount;
    }

    public Pair<Double, Double>[] getCoords() {
        return coords;
    }

    public double getDistanceBetween(int teamA, int teamB) {
        if (teamA >= teamCount || teamB >= teamCount) {
            throw new ArrayIndexOutOfBoundsException("Teamindex to high");
        } else {
            return Math.sqrt(Math.pow(coords[teamA].getKey() - coords[teamB].getKey(), 2) + Math.pow(coords[teamA].getValue() - coords[teamB].getValue(), 2));
        }
    }
}
