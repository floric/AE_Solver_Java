package org.floric.runningdinner.util;

import javafx.util.Pair;
import java.lang.Math;

/**
 * Created by Florian on 24.02.2016.
 */
public final class MathUtil {

    private MathUtil() {
    }

    public static double getDistanceBetween(Pair<Double, Double> teamA, Pair<Double, Double> teamB) {
            return  Math.sqrt(Math.pow(teamA.getKey() - teamB.getKey(), 2) + Math.pow(teamA.getValue() - teamB.getValue(), 2));
    }
}
