package org.floric.runningdinner.main.core;

import org.floric.runningdinner.main.base.IPersistent;

import java.awt.geom.Point2D;
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

    private String address = "Street, City";
    private Point2D location = new Point2D.Double(0, 0);

    public Team(Person pA, Person pB) {
        if (pA == null || pB == null) {
            throw new IllegalArgumentException();
        }

        this.teamIndex = Team.getNextIndex();
        this.pA = pA;
        this.pB = pB;

        Core.getInstance().addTeam(this);
        Core.getInstance().setToDirtySafeState();
    }

    public static int getNextIndex() {
        return countingTeamIndex++;
    }

    public static Point2D getMinLocation(List<Team> teams) {
        Point2D min = new Point2D.Double(teams.stream().mapToDouble(value -> value.getLocation().getX()).min().getAsDouble(),
                teams.stream().mapToDouble(value -> value.getLocation().getY()).min().getAsDouble());
        return min;
    }

    public static Point2D getMaxLocation(List<Team> teams) {
        Point2D min = new Point2D.Double(teams.stream().mapToDouble(value -> value.getLocation().getX()).max().getAsDouble(),
                teams.stream().mapToDouble(value -> value.getLocation().getY()).max().getAsDouble());
        return min;
    }

    public int getTeamIndex() {
        return teamIndex;
    }

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point2D location) {
        this.location = location;
        Core.getInstance().setToDirtySafeState();
    }

    public Person getPersonA() {
        return pA;
    }

    public Person getPersonB() {
        return pB;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;

        Core.getInstance().setToDirtySafeState();
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
        objs.add(getAddress());
        objs.add(String.valueOf(getLocation().getX()));
        objs.add(String.valueOf(getLocation().getY()));

        return objs;
    }

}
