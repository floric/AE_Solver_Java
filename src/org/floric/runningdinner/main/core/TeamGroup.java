package org.floric.runningdinner.main.core;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by florian on 28.02.2016.
 */
public class TeamGroup {

    private ArrayList<Team> teams = new ArrayList<>();

    public TeamGroup() {

    }

    public int getGroupIndex() {
        Core c = Core.getInstance();

        return 0;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    protected void addTeam(Team t) {
        if (t != null) {
            int teamIndex = teams.indexOf(t);
            if(teamIndex != -1) {
                teams.set(teamIndex, t);
            } else {
                teams.add(t);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    protected void removeTeam(Team t) {
        teams.remove(t);
    }

    public boolean containsTeam(Team t) {
        return teams.contains(t);
    }

    public double getTotalDistanceToCenter(Point2D center) {
        return teams.stream().mapToDouble(team -> team.getLocation().distance(center)).sum();
    }

    public Point2D getCenter() {
        double x = teams.stream().mapToDouble(value -> value.getLocation().getX()).average().getAsDouble();
        double y = teams.stream().mapToDouble(value -> value.getLocation().getY()).average().getAsDouble();

        return new Point2D.Double(x, y);
    }
}
