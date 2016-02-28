package org.floric.runningdinner.util;

import javafx.util.Pair;

import java.lang.Math;

/**
 * Created by Florian on 24.02.2016.
 */
public final class MathUtil<T extends Number> {

    private MathUtil() {
    }

    public static double getDistanceBetween(Pair<? super Number, ? super Number> teamA, Pair<? super Number, ? super Number> teamB) {
        return Math.sqrt(Math.pow((double) teamA.getKey() - (double) teamB.getKey(), 2) + Math.pow((double) teamA.getValue() - (double) teamB.getValue(), 2));
    }
}
