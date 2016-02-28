package org.floric.runningdinner.main.base;

import javafx.util.Pair;

/**
 * Created by Florian on 24.02.2016.
 */
public interface ISolver {

    int[][] getSolution();

    double getNormalizedProgress();

    void setTeamCount();

    void setCoordinates(Pair<Double, Double>[] coords);
}
