package org.floric.runningdinner.main.base;

import javafx.geometry.Point2D;
import org.floric.runningdinner.main.core.Core;
import org.floric.runningdinner.main.core.Team;
import org.floric.runningdinner.main.core.TeamGroup;
import org.floric.runningdinner.util.DataGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by florian on 28.02.2016.
 */
public class TeamGroupTest {

    private TeamGroup t;
    private DataGenerator dg;

    @Before
    public void setUp() throws Exception {
        t = new TeamGroup();
        dg = Core.getInstance().getDataGenerator();
    }


}