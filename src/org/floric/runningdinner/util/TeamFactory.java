package org.floric.runningdinner.util;

import javafx.geometry.Point2D;
import org.floric.runningdinner.main.base.IPersistentFactory;
import org.floric.runningdinner.main.core.Logger;
import org.floric.runningdinner.main.core.Person;
import org.floric.runningdinner.main.core.Team;

/**
 * Created by florian on 22.03.2016.
 */
public class TeamFactory implements IPersistentFactory {

    @Override
    public Team getInstanceFromString(String str) {
        String[] parts = str.split("\\|");

        // stop at parse error
        if (parts.length != 4) {
            Logger.Log(Logger.LOG_VERBOSITY.ERROR, "Team read error!");
        }

        Team t = new Team(new Person(parts[0]), new Person(parts[1]));
        t.setLocation(new Point2D(Float.valueOf(parts[2]), Float.valueOf(parts[3])));

        Logger.Log(Logger.LOG_VERBOSITY.INFO, "Read team: " + t.toString());

        return t;
    }
}
