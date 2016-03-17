package org.floric.runningdinner.util;

import org.floric.runningdinner.main.base.IPersistent;
import org.floric.runningdinner.main.core.Core;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/** Writing/Reading files
 *
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

    public void writeFile(String filePath) throws IOException {
        objects.entrySet().forEach(entry -> {
            strBld.append("[[").append(entry.getKey()).append("]]\n");
            entry.getValue().forEach(obj -> strBld.append(obj.writeObject()));
        });

        List<String> lines = Arrays.asList(strBld.toString().split("\n"));
        Files.write(Paths.get(Core.getInstance().getSafePath()), lines, Charset.forName("UTF-8"));
    }


}
