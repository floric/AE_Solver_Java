package org.floric.runningdinner.main.base;

import javafx.util.Pair;

/**
 * Created by Florian on 24.02.2016.
 */
public interface ISolver {

    public int[][] getSolution();
    public double getNormalizedProgress();
    public void setTeamCount();
    public void setCoordinates(Pair<Double, Double>[] coords);
}
