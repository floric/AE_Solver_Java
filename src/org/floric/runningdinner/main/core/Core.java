package org.floric.runningdinner.main.core;

import org.floric.runningdinner.main.base.TeamGroup;
import org.floric.runningdinner.util.DataGenerator;

import java.util.ArrayList;

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

    private ArrayList<TeamGroup> groups = new ArrayList<>();

    private Core() {

    }

    // use Singleton pattern
    public static Core getInstance() {
        if (core == null) {
            core = new Core();
        }

        return core;
    }

    public DataGenerator getDataGenerator() {
        return dataGen;
    }

    public void addTeamGroup(TeamGroup t) {
        if (t == null) {
            throw new IllegalArgumentException("Teamgroup null!");
        }
        if (groups.contains(t)) {
            throw new IllegalArgumentException("Teamgroup already added!");
        }

        groups.add(t);
    }

    public ArrayList<TeamGroup> getTeamsGroups() {
        return groups;
    }

    public TeamGroup getTeamGroupByIndex(int i) {
        if (i < 0 || i >= groups.size()) {
            throw new IndexOutOfBoundsException("Teamindex unknown!");
        }
        return groups.get(i);
    }
}
