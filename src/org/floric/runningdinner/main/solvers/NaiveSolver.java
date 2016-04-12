package org.floric.runningdinner.main.solvers;

import javafx.util.Pair;
import org.floric.runningdinner.main.base.ISolver;
import org.floric.runningdinner.main.core.Logger;
import org.floric.runningdinner.util.MathUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Florian on 24.02.2016.
 */
final public class NaiveSolver implements ISolver {

    private static int MEALS_COUNT = 3;
    private Pair<Double, Double>[] coords;
    private Integer[][] s;

    @Override
    public Integer[][] calcSolution(Pair<Double, Double>[] coords) {
        this.coords = coords;

        initSolutionMatrix();
        findPossibilities();


        Logger.Log(Logger.LOG_VERBOSITY.MAIN, "Solution:");
        MathUtil.printMatrix(s);

        return s;
    }

    private List<Integer[][]> findPossibilities() {
        List<Integer[][]> possibilities = new LinkedList<>();


        return possibilities;
    }

    private void initSolutionMatrix() {
        s = new Integer[coords.length][MEALS_COUNT];
        for (int i = 0; i < coords.length; i++) {
            for (int k = 0; k < MEALS_COUNT; k++) {
                s[i][k] = 0;
            }
        }
    }

    @Override
    public double getNormalizedProgress() {
        return 0;
    }
}
