package org.floric.runningdinner.util;

import org.floric.runningdinner.main.base.IPersistent;

import java.util.*;

/**
 * Created by florian on 07.03.2016.
 */
public class DataWriterReader {

    private StringBuilder strBld = new StringBuilder();
    private Map<String, List<IPersistent>> objects = new HashMap<>();

    public void reset() {
        strBld = new StringBuilder();
    }

    public void addObject(IPersistent obj) {
        if(objects.containsKey(obj.getType())) {
            List<IPersistent> listOfObjects = objects.get(obj.getType());
            listOfObjects.add(obj);
        } else {
            List<IPersistent> newList = new LinkedList<>();
            newList.add(obj);
            objects.put(obj.getType(), newList);
        }
    }

    public void writeFile(String filePath) {
        objects.entrySet().forEach(entry -> {
            strBld.append("[[" + entry.getKey() + "]]\n");
            entry.getValue().forEach(obj -> {
                strBld.append(obj.writeObject());
            });
        });

        System.out.println("OUTPUT:");
        System.out.println(strBld.toString());
    }


}
