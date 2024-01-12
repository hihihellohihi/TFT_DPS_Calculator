package reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class ProcessItems {

    public HashMap<String, HashMap<String, String>> items = new HashMap<String, HashMap<String, String>>();

    public void readItemCSV(String fileName) throws IOException {
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        String[] titleLine = br.readLine().split(",");
        int length = titleLine.length;
        while (br.ready()){
            // split each line in CSV by comma
            String[] line = br.readLine().split(",", length);
            for (int i = 0; i < length; i++) {
                line[i] = line[i].replaceAll("AD", "ADMult");
                line[i] = line[i].replaceAll("AP", "APMult");
                line[i] = line[i].replaceAll("AS", "ASMult");
            }
            HashMap<String, String> itemInfo = new HashMap<String, String>();
            // item stats
            itemInfo.put("Stats", line[1]);
            // other info for each item
            itemInfo.put("x", line[2]);
            itemInfo.put("y", line[3]);
            itemInfo.put("Duration", line[4]);
            itemInfo.put("z", line[5]);
            itemInfo.put("Stacks Cap", line[6]);
            items.put(line[0], itemInfo);
        }
    }

}
