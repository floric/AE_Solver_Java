package org.floric.runningdinner.main.base;

import org.floric.runningdinner.main.core.Team;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by Florian on 27.02.2016.
 */
public interface ICluster {

    void setPoints(ArrayList<Team> teams);

    void clusterPoints(int expectedClasses, ArrayList<Team> teams);

    ArrayList<Point2D> getCenters();

    default double getError() {
        return Double.POSITIVE_INFINITY;
    }
}
