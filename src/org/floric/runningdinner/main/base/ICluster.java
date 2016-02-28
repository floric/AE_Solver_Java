package org.floric.runningdinner.main.base;

import javafx.geometry.Point2D;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Florian on 27.02.2016.
 */
public interface ICluster {

    void setPoints(ArrayList<Team> teams);

    void clusterPoints(int expectedClasses, ArrayList<Team> teams);

    ArrayList<Point2D> getCenters();

    ArrayList<TeamGroup> getClusteredPoints();

    default double getError() {
        return Double.POSITIVE_INFINITY;
    }
}
