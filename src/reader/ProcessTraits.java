package reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ProcessTraits {

    public HashMap<String, HashMap<String, String>> traits = new HashMap<String, HashMap<String, String>>();

    public void readTraitCSV(String fileName) throws IOException {
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
            HashMap<String, String> traitInfo = new HashMap<String, String>();
            // one entry for each possible trait breakpoint
            for (var i = 1; i <= 10; i++) {
                if (!(Objects.equals(line[i], ""))) {
                    traitInfo.put(String.valueOf(i), line[i]);
                }
            }
            // other info for each trait
            traitInfo.put("x", line[11]);
            traitInfo.put("y", line[12]);
            traitInfo.put("Duration", line[13]);
            traitInfo.put("z", line[14]);
            traitInfo.put("Stacks Cap", line[15]);
            traits.put(line[0], traitInfo);
        }
    }

}
