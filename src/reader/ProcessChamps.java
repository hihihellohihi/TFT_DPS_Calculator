package reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ProcessChamps {

    public HashMap<String, HashMap<String, String>> champs = new HashMap<String, HashMap<String, String>>();

    public void readChampCSV(String fileName) throws IOException {
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        String[] titleLine = br.readLine().split(",");
        while (br.ready()){
            // split each line in CSV by comma
            String[] line = br.readLine().split(",");
            HashMap<String, String> champInfo = new HashMap<String, String>();
            // champ stats
            for (var i = 0; i <= 14; i++) {
                if (!(Objects.equals(line[i], ""))) {
                    champInfo.put(titleLine[i], line[i]);
                }
            }
            // traits for each champ
            int j = 15;
            StringBuilder traits = new StringBuilder();
            while (j < line.length) {
                traits.append(line[j]);
                traits.append(",");
                j ++;
            }
            champInfo.put("Traits", traits.toString());
            champs.put(line[0], champInfo);
        }
    }

}
