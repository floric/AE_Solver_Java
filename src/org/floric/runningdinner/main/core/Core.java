package org.floric.runningdinner.main.core;

import javafx.application.Platform;
import org.floric.runningdinner.main.base.IObserver;
import org.floric.runningdinner.main.base.IPersistent;
import org.floric.runningdinner.util.DataGenerator;
import org.floric.runningdinner.util.DataWriterReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Core class
 *
 * Created by Florian on 22.02.2016.
 */
public class Core implements IPersistent {

    // constants
    public final static int TEAMS_MAX = 99;
    public final static int TEAMS_MIN = 3;
    public final static int TEAMS_DEFAULT = 9;

    public final static int SEED_MIN = 1;
    public final static int SEED_MAX = 1000;
    public final static int SEED_DEFAULT = 1;

    public final static String SAFE_EXTENSION = "rdsv";
    public final static String SAFE_FILENAME = "rdsafe";

    private static Core core = null;

    private DataGenerator dataGen = new DataGenerator(TEAMS_DEFAULT, SEED_DEFAULT);

    private Map<Integer, Team> teams = new HashMap<>();
    private Map<Integer, TeamGroup> groups = new HashMap<>();

    private String safeDir = new String(System.getProperty("user.home"));

    private List<IObserver> observers = new LinkedList<>();

    private Core() {
        // basic group for every new team
        Init();
    }

    // use Singleton pattern
    public static Core getInstance() {
        if (core == null) {
            core = new Core();
        }

        return core;
    }

    private void Init() {
        groups.put(0, new TeamGroup());
        Logger.Log(Logger.LOG_VERBOSITY.INFO, "Core initialized.");
    }

    public void addObserver(IObserver obj) {
        observers.add(obj);
    }

    public void notifyObservers() {
        observers.forEach(obj -> obj.update());
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
        teams.put(t.getTeamIndex(), t);
    }

    public boolean removeTeam(Team t) {
        teams.remove(t.getTeamIndex());
        t.getCurrentGroup().removeTeam(t);

        return true;
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

    public String getSafePath() {
        return safeDir + "\\" + SAFE_FILENAME + "." + SAFE_EXTENSION;
    }

    public String getSafeDir() {
        return safeDir;
    }

    public void setSafeDir(String str) {
        safeDir = str;
        notifyObservers();
    }

    public void Reset() {
        teams.clear();
        groups.clear();

        Init();
    }

    public void exit() {
        Logger.Log(Logger.LOG_VERBOSITY.INFO, "Exit.");
        Platform.exit();
    }

    public void writeSafeFile() {
        DataWriterReader dataRW = new DataWriterReader();

        // add core and static items for saving
        dataRW.addObject(this);
        teams.forEach((index, obj) -> dataRW.addObject(obj));

        try {
            dataRW.writeFile(getSafePath());

            Logger.Log(Logger.LOG_VERBOSITY.MAIN, "Safe successfull.");
        } catch (IOException e) {
            Logger.Log(Logger.LOG_VERBOSITY.ERROR, "Safefile writing failed!");
        }
    }

    public void readSafeFile() {
        DataWriterReader dataRW = new DataWriterReader();
        try {
            List<IPersistent> readObjs = dataRW.readFile(getSafePath());
            Logger.Log(Logger.LOG_VERBOSITY.ERROR, "Read " + readObjs.size() + " elements!");

            Logger.Log(Logger.LOG_VERBOSITY.INFO, "Safefile successfully read.");
        } catch (IOException e) {
            Logger.Log(Logger.LOG_VERBOSITY.ERROR, "Safefile reading failed!");
        }
    }

    @Override
    public List<String> getDataFromObject() {
        List<String> data = new LinkedList<>();
        data.add(getSafeDir());

        return data;
    }

    @Override
    public String getType() {
        return "Core";
    }
}
