package org.floric.runningdinner.main.base;

import java.util.List;

/** Persistent interface
 *
 * Created by florian on 07.03.2016.
 */
public interface IPersistent {

    List<String> getDataFromObject();
    String getType();
}
