package org.floric.runningdinner.main.core;

import org.floric.runningdinner.util.DataGenerator;

/**
 * Created by Florian on 22.02.2016.
 */
public class Core {

    public final static int TEAMS_MAX = 99;
    public final static int TEAMS_MIN = 3;
    public final static int TEAMS_DEFAULT = 9;

    public final static int SEED_MIN = 1;
    public final static int SEED_MAX = 1000;
    public final static int SEED_DEFAULT = 1;

    private static Core core = null;
    private DataGenerator dataGen = new DataGenerator(TEAMS_DEFAULT, SEED_DEFAULT);

    private boolean needsRecalculation = false;

    public boolean isNeedsSave() {
        return needsSave;
    }

    public void setNeedsSave(boolean needsSave) {
        this.needsSave = needsSave;
    }

    public boolean isNeedsRecalculation() {
        return needsRecalculation;
    }

    public void setNeedsRecalculation(boolean needsRecalculation) {
        this.needsRecalculation = needsRecalculation;
    }

    private boolean needsSave = false;

    private Core() {

    }

    // use Singleton pattern
    public static Core getInstance() {
        if(core == null) {
            core = new Core();
        }

        return core;
    }

    public DataGenerator getDataGenerator() {
        return dataGen;
    }
}
