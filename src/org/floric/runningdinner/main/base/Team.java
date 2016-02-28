package org.floric.runningdinner.main.base;

import javafx.geometry.Point2D;

/**
 * Created by florian on 28.02.2016.
 */
public class Team {

    private Person pA;
    private Person pB;
    private Point2D location = Point2D.ZERO;
    private int groupIndex = 0;

    public Team(Person pA, Person pB) {
        if (pA == null || pB == null) {
            throw new IllegalArgumentException();
        }
        this.pA = pA;
        this.pB = pB;
    }

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public Person getPersonA() {
        return pA;
    }

    public Person getPersonB() {
        return pB;
    }

    public int getGroupIndex() {
        return groupIndex;
    }

    public void setGroupIndex(int groupIndex) {
        this.groupIndex = groupIndex;
    }

    @Override
    public String toString() {
        return new String("Team (" + pA + " & " + pB + ") in " + groupIndex + " group");
    }
}
