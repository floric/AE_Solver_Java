package org.floric.runningdinner.util;

import org.floric.runningdinner.main.base.ICluster;
import org.floric.runningdinner.main.core.Core;
import org.floric.runningdinner.main.core.Team;
import org.floric.runningdinner.main.core.TeamGroup;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Created by Florian on 27.02.2016.
 */
public class MeanCluster implements ICluster {

    private static final int ITERATIONS = 10;
    private ArrayList<Team> teams = new ArrayList<>();
    private ArrayList<TeamGroup> clusters = new ArrayList<>();
    private ArrayList<Point2D> centers = new ArrayList<>();

    public MeanCluster() {

    }

    @Override
    public void setPoints(ArrayList<Team> teams) {
        this.teams = teams;
    }

    @Override
    public void clusterPoints(int expectedClasses, ArrayList<Team> teams) {
        Core c = Core.getInstance();

        if (expectedClasses <= 0) {
            throw new IllegalArgumentException("At least 1 center for clustering needed!");
        }

        double minTotalDistance = Double.POSITIVE_INFINITY;
        int iterations = 0;

        // set random centers for every cluster in min/max range
        IntStream.range(0, expectedClasses).forEach(centerIndex -> {
            centers.add(c.getDataGenerator().getRandomPoint(Team.getMinLocation(teams), Team.getMaxLocation(teams)));
        });


        while(iterations < ITERATIONS) {

            // assign teams to centers by smallest distance
            double minDistance = Double.POSITIVE_INFINITY;
            int minIndex = 0;

            teams.forEach(currentTeam -> {
                Point2D nearestCenter = IntStream.range(0, centers.size()).mapToObj(groupIndex -> centers.get(groupIndex)).min(
                        (o1, o2) -> (o1.distance(currentTeam.getLocation()) - o2.distance(currentTeam.getLocation()) > 0) ? 1 : -1).get();
                c.setTeamToGroup(currentTeam, centers.indexOf(nearestCenter));
            });

            // calculate total distance to centers for every team
            double totalDistance = 0.0;

            iterations++;
        }
    }

    @Override
    public ArrayList<Point2D> getCenters() {
        return centers;
    }

    @Override
    public double getError() {
        return 0;
    }
}
