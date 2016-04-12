package org.floric.runningdinner.main.base;

import javafx.util.Pair;

/**
 * Created by Florian on 24.02.2016.
 */
public interface ISolver {

    Integer[][] calcSolution(Pair<Double, Double>[] coords);

    double getNormalizedProgress();
}
