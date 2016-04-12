package org.floric.runningdinner.main.core;

import javafx.scene.paint.Color;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by florian on 28.02.2016.
 */
public class TeamGroup {

    private Set<Team> teams = new HashSet<>();
    private int groupIndex = 0;
    private Color col = new Color(0.0, 0.0, 0.0, 1.0);

    public TeamGroup(int groupIndex) {
        this.groupIndex = groupIndex;
    }

    public int getGroupIndex() {
        return groupIndex;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    protected void addTeam(Team t) {
        if (t != null) {
            teams.add(t);
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

    public double getVariance(Point2D centerPt) {
        return 0;
    }

    public Color getColor() {
        return col;
    }

    public void setColor(Color c) {
        col = c;
    }
}
