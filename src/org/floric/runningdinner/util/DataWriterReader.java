package org.floric.runningdinner.util;

import org.floric.runningdinner.main.base.IPersistent;
import org.floric.runningdinner.main.core.Core;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/** Writing/Reading files
 *
 * Created by florian on 07.03.2016.
 */
public class DataWriterReader {

    public static final String DELIMITER = "|";
    public static final String LINEBREAK = "\n";

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
            strBld.append("[[").append(entry.getKey()).append("]]" + LINEBREAK);

            // add control value for lines count
            strBld.append(entry.getValue().size()).append(LINEBREAK);

            // add data
            entry.getValue().forEach(obj -> {
                List<String> data = obj.getDataFromObject();
                data.forEach(s -> strBld.append(s).append(DELIMITER));
                strBld.append(LINEBREAK);
            });
        });

        List<String> lines = Arrays.asList(strBld.toString().split("\n"));

        Files.write(Paths.get(Core.getInstance().getSafePath()), lines, Charset.forName("UTF-8"));
    }

    public List<IPersistent> readFile(String filePath) throws IOException {
        List<IPersistent> readObjs = new LinkedList<>();

        String[] readLines = Files.lines(Paths.get(filePath)).toArray(size -> new String[size]);

        for (int i = 0; i < readLines.length; i++) {
            String line = readLines[i];

            if (line.matches("\\[\\[\\w*\\]\\]")) {
                String foundIdentifier = line.substring(2, line.length() - 2);
                int linesCount = Integer.valueOf(readLines[i + 1]);

                /*// differ types
                if(foundIdentifier.compareTo("TeamBox") == 0) {

                    for (int j = 0; j < linesCount / elem.getUsedLines(); j++) {

                    }


                    readObjs.add(elem);

                    Logger.Log(Logger.LOG_VERBOSITY.MAIN, "Read Teambox");
                } else if(foundIdentifier.compareTo("Core") == 0) {
                    Core.getInstance().readData(setSubListFromStringArr(readLines, i + 2, i + 2 + linesCount).stream().reduce(String::concat).get());

                    Logger.Log(Logger.LOG_VERBOSITY.MAIN, "Read Core");
                } else {
                    Logger.Log(Logger.LOG_VERBOSITY.ERROR, "Unknown file type!");
                }*/
            }
        }

        return readObjs;
    }

    private List<String> setSubListFromStringArr(String[] arr, int start, int end) {
        List<String> lst = new ArrayList<>();

        for (int i = start; i < end; i++) {
            lst.add(arr[i]);
        }

        return lst;
    }
}
