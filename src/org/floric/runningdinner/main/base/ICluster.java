package org.floric.runningdinner.main.base;

import org.floric.runningdinner.main.core.Team;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * Created by Florian on 27.02.2016.
 */
public interface ICluster {

    void setPoints(List<Team> teams);

    void clusterPoints(int expectedClasses, List<Team> teams, boolean clusterEqual);

    List<Point2D> getCenters();

    default double getError() {
        return Double.POSITIVE_INFINITY;
    }
}
