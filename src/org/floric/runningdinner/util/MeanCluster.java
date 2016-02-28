package org.floric.runningdinner.util;

import javafx.geometry.Point2D;
import javafx.util.Pair;
import org.floric.runningdinner.main.base.ICluster;
import org.floric.runningdinner.main.base.Team;
import org.floric.runningdinner.main.base.TeamGroup;
import org.floric.runningdinner.main.core.Core;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Florian on 27.02.2016.
 */
public class MeanCluster implements ICluster {

    private ArrayList<Team> teams = new ArrayList<>();
    private ArrayList<TeamGroup> clusters = new ArrayList<>();
    private static final int ITERATIONS = 10000;

    public MeanCluster() {

    }

    @Override
    public void setPoints(ArrayList<Team> teams) {
        this.teams = teams;
    }

    @Override
    public void clusterPoints(int expectedClasses, ArrayList<Team> teams) {
        if (expectedClasses <= 0) {
            throw new IllegalArgumentException("At least 1 center for clustering needed!");
        }

        // create groups and first centers
        for (int i = 0; i < expectedClasses; i++) {
            clusters.add(new TeamGroup());
        }

        // find min / max of teams for random center borders
        Point2D min = new Point2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        Point2D max = new Point2D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        for(Team t: teams) {
            if(t.getLocation().getX() > max.getX()) {
                max = new Point2D(t.getLocation().getX(), max.getY());
            } else if(t.getLocation().getX() < min.getX()) {
                min = new Point2D(t.getLocation().getX(), min.getY());
            }

            if(t.getLocation().getY() > max.getY()) {
                max = new Point2D(max.getX(), t.getLocation().getY());
            } else if(t.getLocation().getY() < min.getY()) {
                min = new Point2D(min.getX(), t.getLocation().getY());
            }
        }

        double minTotalDistance = Double.POSITIVE_INFINITY;
        int iterations = 0;

        while(iterations < ITERATIONS) {

            // set random centers for every cluster
            for(TeamGroup tg: clusters) {
                tg.setCenter(Core.getInstance().getDataGenerator().getRandomPoint(min, max));
            }

            // assign teams to centers by smallest distance
            double minDistance = Double.POSITIVE_INFINITY;
            int minIndex = 0;

            for(Team t: teams) {
                int currentIndex = 0;
                double currentDistance = Double.POSITIVE_INFINITY;

                for(TeamGroup tg: clusters) {
                    currentDistance = t.getLocation().distance(tg.getCenter());
                    if(currentDistance < minDistance) {
                        minIndex = currentIndex;
                        minDistance = currentDistance;
                    }

                    currentIndex++;
                }

                t.setGroupIndex(minIndex);
            }

            // calculate total distance to centers for every team
            double totalDistance = 0.0;
            for(Team t: teams) {
                totalDistance += t.getLocation().distance(clusters.get(t.getGroupIndex()).getCenter());
            }

            if(totalDistance < minTotalDistance) {
                for(int groupIndex = 0; groupIndex < clusters.size(); groupIndex++) {
                    for(Team t: teams) {
                        if(t.getGroupIndex() == groupIndex) {
                            TeamGroup tg = clusters.get(groupIndex);
                            tg.addTeam(t);
                        }
                    }
                }

                System.out.println("Iteration " + iterations);
                System.out.println("New min distance:" + totalDistance);

                minTotalDistance = totalDistance;
            }

            iterations++;
        }
    }

    @Override
    public ArrayList<Point2D> getCenters() {
        ArrayList<Point2D> centers = new ArrayList<>();
        for(TeamGroup tg: clusters) {
            centers.add(tg.getCenter());
        }

        return centers;
    }

    @Override
    public ArrayList<TeamGroup> getClusteredPoints() {
        return clusters;
    }

    @Override
    public double getError() {
        return 0;
    }
}
