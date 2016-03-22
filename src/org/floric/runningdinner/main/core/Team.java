package org.floric.runningdinner.main.core;

import javafx.geometry.Point2D;
import org.floric.runningdinner.main.base.IPersistent;

import java.util.LinkedList;
import java.util.List;

/** Team class
 *
 * Created by florian on 28.02.2016.
 */
public class Team implements IPersistent {

    private static int countingTeamIndex = 0;

    private Person pA;
    private Person pB;
    private int teamIndex;
    private Point2D location = Point2D.ZERO;

    public Team(Person pA, Person pB) {
        if (pA == null || pB == null) {
            throw new IllegalArgumentException();
        }

        this.teamIndex = Team.getNextIndex();
        this.pA = pA;
        this.pB = pB;

        Core.getInstance().addTeam(this);
        Core.getInstance().writeSafeFile();
    }

    public static int getNextIndex() {
        return countingTeamIndex++;
    }

    public int getTeamIndex() {
        return teamIndex;
    }

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point2D location) {
        this.location = location;
        Core.getInstance().writeSafeFile();
    }

    public Person getPersonA() {
        return pA;
    }

    public Person getPersonB() {
        return pB;
    }

    public int getGroupIndex() {
        return Core.getInstance().getTeamGroupIndex(
                Core.getInstance().getTeamsGroups().stream().filter(teamGroup -> teamGroup.containsTeam(this)).findFirst().get()
        );
    }

    public TeamGroup getCurrentGroup() {
        return Core.getInstance().getTeamsGroups().stream().filter(teamGroup -> teamGroup.containsTeam(this)).findFirst().get();
    }

    @Override
    public String toString() {
        return new String("Team (" + pA + " & " + pB + ") in " + getGroupIndex() + " group");
    }

    @Override
    public String getType() {
        return "Team";
    }

    @Override
    public List<String> getDataFromObject() {
        List<String> objs = new LinkedList<>();

        objs.add(getPersonA().toString());
        objs.add(getPersonB().toString());
        objs.add(String.valueOf(getLocation().getX()));
        objs.add(String.valueOf(getLocation().getY()));

        return objs;
    }
}
