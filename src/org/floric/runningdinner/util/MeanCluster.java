package org.floric.runningdinner.util;

import javafx.util.Pair;
import org.floric.runningdinner.main.base.ICluster;

import java.util.ArrayList;

/**
 * Created by Florian on 27.02.2016.
 */
public class MeanCluster implements ICluster {

    private Pair<Double, Double>[] centers;
    private ArrayList<Pair<? super Number, ? super Number>[]> coordinates;
    private int expectedClasses = 0;

    public MeanCluster(int expectedClasses) {
        if (expectedClasses <= 0) {
            throw new IllegalArgumentException("At least 1 center for clustering needed!");
        }

        this.expectedClasses = expectedClasses;
        centers = new Pair[expectedClasses];
    }

    @Override
    public void setPoints(Pair<? super Number, ? super Number>[] coordinates) {
        this.coordinates.add(coordinates);
    }

    @Override
    public void clusterPoints() {
        // K Mean Cluster algorithm
    }

    @Override
    public Pair<Double, Double>[] getCenters() {
        return centers;
    }

    @Override
    public ArrayList<Pair<Double, Double>[]> getClusteredPoints() {
        return null;
    }

    @Override
    public double getError() {
        return 0;
    }
}
