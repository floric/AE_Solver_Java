package org.floric.runningdinner.main.core;

import org.floric.runningdinner.util.DataGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private Map<Integer, TeamGroup> groups = new HashMap<>();

    private Core() {
        // basic group for every new team
        Init();
    }

    private void Init() {
        groups.put(0, new TeamGroup());
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

    public void setTeamGroup(TeamGroup t, int groupIndex) {
        if (t == null) {
            throw new IllegalArgumentException("Teamgroup null!");
        }

        groups.put(groupIndex, t);
    }

    public void addTeam(Team t) {
        groups.get(0).addTeam(t);
    }

    public void setTeamToGroup(Team t, int groupIndex) {
        t.getCurrentGroup().removeTeam(t);
        getTeamGroup(groupIndex).addTeam(t);
    }

    public TeamGroup getTeamGroup(int groupIndex) {
        if(groups.containsKey(groupIndex)) {
            return groups.get(groupIndex);
        } else {
            TeamGroup newGroup = new TeamGroup();
            groups.put(groupIndex, newGroup);
            return newGroup;
        }
    }

    public List<TeamGroup> getTeamsGroups() {
        // only return groups with more then one team
        return groups.values().stream().filter(teamGroup -> teamGroup.getTeams().size() > 0).collect(Collectors.toList());
    }

    public int getTeamGroupIndex(TeamGroup t) {
        return groups.entrySet().stream().filter(integerTeamGroupEntry -> integerTeamGroupEntry.getValue() == t).findFirst().get().getKey();
    }

    public void Reset() {
        groups.clear();
        Init();
    }

}
