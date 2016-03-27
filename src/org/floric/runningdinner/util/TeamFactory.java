package org.floric.runningdinner.util;

import org.floric.runningdinner.main.base.IPersistentFactory;
import org.floric.runningdinner.main.core.Logger;
import org.floric.runningdinner.main.core.Person;
import org.floric.runningdinner.main.core.Team;

import java.awt.*;

/**
 * Created by florian on 22.03.2016.
 */
public class TeamFactory implements IPersistentFactory {

    @Override
    public Team getInstanceFromString(String str) {
        String[] parts = str.split("\\|");

        // stop at parse error
        if (parts.length != 5) {
            Logger.Log(Logger.LOG_VERBOSITY.ERROR, "Team read error!");
            return null;
        }

        Team t = new Team(new Person(parts[0]), new Person(parts[1]));
        t.setAddress(parts[2]);
        t.setLocation(new Point.Double(Float.valueOf(parts[3]), Float.valueOf(parts[4])));

        Logger.Log(Logger.LOG_VERBOSITY.INFO, "Read team: " + t.toString());

        return t;
    }
}
