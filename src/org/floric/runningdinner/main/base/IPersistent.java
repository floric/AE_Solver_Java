package org.floric.runningdinner.main.base;

/**
 * Created by florian on 07.03.2016.
 */
public interface IPersistent {

    String writeObject();
    String getType();
    void readObject(String data);
}
