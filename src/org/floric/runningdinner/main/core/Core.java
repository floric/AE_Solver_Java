package org.floric.runningdinner.main.core;

import javafx.application.Platform;
import org.floric.runningdinner.main.base.IObservable;
import org.floric.runningdinner.main.base.IObserver;
import org.floric.runningdinner.main.base.IPersistent;
import org.floric.runningdinner.util.DataGenerator;
import org.floric.runningdinner.util.DataWriterReader;
import org.reactfx.util.FxTimer;

import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/** Core class
 *
 * Created by Florian on 22.02.2016.
 */
public class Core implements IPersistent, IObservable {

    // constants
    public final static int TEAMS_MAX = 99;
    public final static int TEAMS_MIN = 3;
    public final static int TEAMS_DEFAULT = 9;

    public final static int SEED_MIN = 1;
    public final static int SEED_MAX = 1000;
    public final static int SEED_DEFAULT = 1;

    public final static int SAFE_DELAY_SEC = 10;
    public final static int DISPLAY_DURATION_SEC = 3;

    public final static String SAFE_EXTENSION = "rdsv";
    public final static String SAFE_FILENAME = "rdsafe";

    private static Core core = null;

    private DataGenerator dataGen = new DataGenerator(TEAMS_DEFAULT, SEED_DEFAULT);
    private Map<Integer, Team> teams = new HashMap<>();
    private Map<Integer, TeamGroup> groups = new HashMap<>();
    private String safeDir = new String(System.getProperty("user.home"));
    private List<IObserver> observers = new LinkedList<>();
    private boolean needsSafe = true;
    private boolean isCurrentlySaving = false;
    private Timer safeTimer = new Timer("Safetimer");

    private Core() {
        // basic group for every new team
        init();
    }

    // use Singleton pattern
    public static Core getInstance() {
        if (core == null) {
            core = new Core();
        }

        return core;
    }

    private void init() {
        groups.put(0, new TeamGroup());

        FxTimer.runPeriodically(
                Duration.ofSeconds(SAFE_DELAY_SEC),
                () -> checkSafeState());

        Logger.Log(Logger.LOG_VERBOSITY.INFO, "Core initialized.");
    }

    private void checkSafeState() {
        if (needsSafe()) {
            writeSafeFile();

            needsSafe = false;
        }

        //EventStreams.ticks(Duration.ofSeconds(5)).subscribe(tick -> Logger.Log(Logger.LOG_VERBOSITY.ERROR, "Test"));
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

        setToDirtySafeState();

        notifyObservers();
    }

    public boolean removeTeam(Team t) {
        teams.remove(t.getTeamIndex());
        t.getCurrentGroup().removeTeam(t);

        setToDirtySafeState();

        notifyObservers();

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

    public List<Team> getTeams() {
        List<Team> teams = new LinkedList<>();
        groups.values().forEach(teamGroup -> teams.addAll(teamGroup.getTeams()));

        return teams;
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

    public boolean needsSafe() {
        return needsSafe;
    }

    public void setToDirtySafeState() {
        if (!needsSafe()) {
            Logger.Log(Logger.LOG_VERBOSITY.MAIN, "Safe needed!");
            this.needsSafe = true;

            notifyObservers();
        }
    }

    public void reset() {
        teams.clear();
        groups.clear();

        init();

        notifyObservers();
    }

    public void exit() {
        if (!isCurrentlySaving) {
            safeTimer.cancel();
            Platform.exit();

            Logger.Log(Logger.LOG_VERBOSITY.INFO, "Exit.");
        } else {
            Logger.Log(Logger.LOG_VERBOSITY.MAIN, "Currently saving!");
        }
    }

    public void writeSafeFile() {
        DataWriterReader dataRW = new DataWriterReader();

        isCurrentlySaving = true;

        // add core and static items for saving
        dataRW.addObject(this);
        teams.forEach((index, obj) -> dataRW.addObject(obj));

        try {
            dataRW.writeFile(getSafePath());

            needsSafe = false;
            isCurrentlySaving = false;

            Logger.Log(Logger.LOG_VERBOSITY.MAIN, "Safe successfull.");
        } catch (IOException e) {
            Logger.Log(Logger.LOG_VERBOSITY.ERROR, "Safefile writing failed!");
        }
    }

    public void readSafeFile() {
        DataWriterReader dataRW = new DataWriterReader();
        try {
            dataRW.readFile(getSafePath());
            checkSafeState();

            Logger.Log(Logger.LOG_VERBOSITY.INFO, "Safefile successfully read.");
        } catch (IOException e) {
            Logger.Log(Logger.LOG_VERBOSITY.ERROR, "Safefile reading failed!");
        }

        notifyObservers();
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
