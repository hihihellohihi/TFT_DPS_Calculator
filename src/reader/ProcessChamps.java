package reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ProcessChamps {

    public HashMap<String, HashMap<String, Float>> champs = new HashMap<String, HashMap<String, Float>>();
    public HashMap<String, String> champTraits = new HashMap<String, String>();

    public void readChampCSV(String fileName) throws IOException {
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        String[] titleLine = br.readLine().split(",");
        while (br.ready()){
            // split each line in CSV by comma
            String[] line = br.readLine().split(",");
            HashMap<String, Float> champInfo = new HashMap<String, Float>();
            // champ stats
            for (var i = 1; i <= 28; i++) {
                try {
                    if (!(Objects.equals(line[i], ""))) {
                        champInfo.put(titleLine[i], Float.parseFloat(line[i]));
                    }
                }
                catch (Exception e) {
                    break;
                }
            }
            // traits for each champ
            int j = 29;
            StringBuilder traits = new StringBuilder();
            while (j < line.length) {
                traits.append(line[j]);
                traits.append(",");
                j ++;
            }
            try {
                champs.put(line[0], champInfo);
                champTraits.put(line[0], traits.toString());
            }
            catch (Exception e) {
                break;
            }
        }
    }

}
