package org.floric.runningdinner.main.base;

import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by Florian on 27.02.2016.
 */
public interface ICluster {

    void setPoints(Pair<? super Number, ? super Number>[] coordinates);

    void clusterPoints();

    Pair<Double, Double>[] getCenters();

    ArrayList<Pair<Double, Double>[]> getClusteredPoints();

    default double getError() {
        return 0;
    }
}
