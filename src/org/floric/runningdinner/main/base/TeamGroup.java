package org.floric.runningdinner.main.base;

import org.floric.runningdinner.main.core.Core;

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
            teams.add(t);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
