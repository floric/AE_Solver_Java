package org.floric.runningdinner.main.base;

import org.floric.runningdinner.main.core.Core;
import org.floric.runningdinner.main.core.TeamGroup;
import org.floric.runningdinner.util.DataGenerator;
import org.junit.Before;

/**
 * Created by florian on 28.02.2016.
 */
public class TeamGroupTest {

    private TeamGroup t;
    private DataGenerator dg;

    @Before
    public void setUp() throws Exception {
        t = new TeamGroup(0);
        dg = Core.getInstance().getDataGenerator();
    }


}