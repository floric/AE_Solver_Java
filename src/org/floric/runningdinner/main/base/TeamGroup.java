package org.floric.runningdinner.main.base;

import javafx.geometry.Point2D;
import org.floric.runningdinner.main.core.Core;

import java.util.ArrayList;

/**
 * Created by florian on 28.02.2016.
 */
public class TeamGroup {

    private ArrayList<Team> teams = new ArrayList<>();

    private Point2D center = Point2D.ZERO;

    public TeamGroup() {

    }

    public int getGroupIndex() {
        Core c = Core.getInstance();
        int index = c.getTeamsGroups().indexOf(this);

        if (index == -1) {
            throw new IllegalStateException("Teamgroup not known in application!");
        }

        return index;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public void addTeam(Team t) {
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

    public double getTotalDistanceToCenter(Point2D center) {
        double sum = 0.0;

        for(Team t: teams) {
            sum += t.getLocation().distance(center);
        }

        return sum;
    }

    public Point2D getCenter() {
        return center;
    }

    public void setCenter(Point2D center) {
        this.center = center;
    }
}
