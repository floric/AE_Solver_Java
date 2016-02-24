package org.floric.runningdinner.main.solvers;

import javafx.util.Pair;
import org.floric.runningdinner.main.base.ISolver;

/**
 * Created by Florian on 24.02.2016.
 */
public class NaiveSolver implements ISolver {
    @Override
    public int[][] getSolution() {
        return new int[0][];
    }

    @Override
    public double getNormalizedProgress() {
        return 0;
    }

    @Override
    public void setTeamCount() {

    }

    @Override
    public void setCoordinates(Pair<Double, Double>[] coords) {

    }
}
