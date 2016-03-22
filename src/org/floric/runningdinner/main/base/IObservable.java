package org.floric.runningdinner.main.base;

/**
 * Created by florian on 22.03.2016.
 */
public interface IObservable {

    void addObserver(IObserver obj);

    void notifyObservers();
}
