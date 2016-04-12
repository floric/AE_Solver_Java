package org.floric.runningdinner.util;

import org.floric.runningdinner.main.base.ICluster;
import org.floric.runningdinner.main.core.Core;
import org.floric.runningdinner.main.core.Logger;
import org.floric.runningdinner.main.core.Team;
import org.floric.runningdinner.main.core.TeamGroup;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by Florian on 27.02.2016.
 */
public class MeanCluster implements ICluster {

    private static final int ITERATIONS = 10;
    private List<Team> teams = new ArrayList<>();
    private List<Point2D> centers = new ArrayList<>();

    public MeanCluster() {

    }

    @Override
    public void setPoints(List<Team> teams) {
        this.teams = teams;
    }

    @Override
    public void clusterPoints(int expectedClasses, List<Team> teams, boolean clusterEqual) {
        Logger.Log(Logger.LOG_VERBOSITY.MAIN, "Cluster " + teams.size() + " teams to " + expectedClasses + " classes!");

        Core c = Core.getInstance();

        if (expectedClasses <= 0) {
            throw new IllegalArgumentException("At least 1 center for clustering needed!");
        }

        // set random centers from teams and add this team to the new group
        Set<Integer> usedTeamIndizes = new HashSet<>();

        for (int centerIndex = 0; centerIndex < expectedClasses; centerIndex++) {
            int usedTeamIndex = 0;

            // get always other index
            do {
                usedTeamIndex = c.getDataGenerator().getNextIndex(0, teams.size());
            } while (usedTeamIndizes.contains(usedTeamIndex));

            Team centeredTeam = teams.get(usedTeamIndex);
            usedTeamIndizes.add(usedTeamIndex);

            centers.add(new Point2D.Double(centeredTeam.getLocation().getX(), centeredTeam.getLocation().getY()));
            c.setTeamToGroup(centeredTeam, centerIndex);
            Logger.Log(Logger.LOG_VERBOSITY.INFO, "Add random center: " + centers.get(centers.size() - 1) + " from team " + centeredTeam.getTeamIndex());
        }

        boolean assignmentsChanged = true;

        int iterations = 0;

        while (assignmentsChanged && iterations < 200) {
            assignmentsChanged = false;
            iterations++;

            for (Team currentTeam : teams) {
                Map<Integer, Double> varianceDiff = new HashMap<>();

                int oldCenterIndex = currentTeam.getGroupIndex();

                // calculate variance difference for all classes when assigning the team to this class
                for (int centerIndex = 0; centerIndex < expectedClasses; centerIndex++) {
                    Point2D centerPt = centers.get(centerIndex);
                    TeamGroup currentGroup = c.getTeamGroup(centerIndex);

                    // set team to other group
                    c.setTeamToGroup(currentTeam, -1);

                    // calculate old variance
                    double oldVariance = currentGroup.getVariance(centerPt);

                    // set team to other group
                    c.setTeamToGroup(currentTeam, centerIndex);

                    // calculate changed variance
                    double centerDistance = centerPt.distance(currentTeam.getLocation());

                    varianceDiff.put(centerIndex, Math.abs(centerDistance));

                    // set team back to old group
                    c.setTeamToGroup(currentTeam, oldCenterIndex);
                }

                int bestCenterIndex = 0;
                if (clusterEqual && iterations > 1) {
                    /*bestCenterIndex = varianceDiff.entrySet().stream()
                            .filter(e -> c.getTeamGroup(e.getKey()).getTeams().size() <= 15)
                            .min((e1, e2) -> (e1.getValue() > e2.getValue()) ? 1 : -1)
                            .get().getKey();*/
                    bestCenterIndex = varianceDiff.entrySet().stream()
                            .min((e1, e2) -> (e1.getValue() > e2.getValue()) ? 1 : -1).get().getKey();
                } else {
                    // get minimum change
                    bestCenterIndex = varianceDiff.entrySet().stream()
                            .min((e1, e2) -> (e1.getValue() > e2.getValue()) ? 1 : -1).get().getKey();
                }

                // check for change
                if (oldCenterIndex != bestCenterIndex) {
                    assignmentsChanged = true;
                }

                // assign team to best fitting group with minimum variance change
                c.setTeamToGroup(currentTeam, bestCenterIndex);
            }

            // recalculate centers
            IntStream.range(0, centers.size()).forEach(centerIndex -> {

                double avgX = c.getTeamGroup(centerIndex).getTeams().stream().mapToDouble(t -> t.getLocation().getX()).average().orElse(0);
                double avgY = c.getTeamGroup(centerIndex).getTeams().stream().mapToDouble(t -> t.getLocation().getY()).average().orElse(0);

                centers.get(centerIndex).setLocation(avgX, avgY);
            });
        }

        Logger.Log(Logger.LOG_VERBOSITY.MAIN, "Finished clustering after " + iterations + " iterations!");
        c.getTeamGroups().forEach(tg -> {
            Logger.Log(Logger.LOG_VERBOSITY.INFO, "Group " + tg.getGroupIndex() + " has " + tg.getTeams().size() + " teams assigned!");
        });

        Core.getInstance().notifyObservers();
    }

    @Override
    public List<Point2D> getCenters() {
        return centers;
    }

    @Override
    public double getError() {
        return 0;
    }
}
