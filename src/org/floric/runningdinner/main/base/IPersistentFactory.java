package org.floric.runningdinner.main.base;

/**
 * Factory to create objects from file
 * <p>
 * Created by florian on 18.03.2016.
 */
public interface IPersistentFactory<T extends IPersistent> {

    T getInstanceFromString(String str);
}
