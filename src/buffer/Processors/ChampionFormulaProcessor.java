package buffer.Processors;

import buffer.IBuffer;
import champion.BuffableChampion;
import champion.IChampion;

public class ChampionFormulaProcessor {
    public static float parseFormula(BuffableChampion champ, String formula) {
        // split by + since it is the lowest in order of operations
        String[] plusFormula = formula.split("\\+");
        if (plusFormula.length == 1) {
            // then split by * if there is no +
            String[] timesFormula = formula.split("\\*");
            // if it's just a single term that can be processed either as a num or a var
            if (timesFormula.length == 1) {
                if (plusFormula[0].equalsIgnoreCase("x")) {
                    return 1 / (champ.stats.get("AS") * (1 + champ.stats.get("ASMult")));
                }
                else if (plusFormula[0].equalsIgnoreCase("-x")) {
                    return -1 / (champ.stats.get("AS") * (1 + champ.stats.get("ASMult")));
                }
                else {
                    return Float.parseFloat(plusFormula[0]);
                }
            }
            else {
                float product = 1;
                for (String formulaPart: timesFormula) {
                    product *= ChampionFormulaProcessor.parseFormula(champ, formulaPart);
                }
                return product;
            }
        }
        else {
            float sum = 0;
            for (String formulaPart: plusFormula) {
                sum += ChampionFormulaProcessor.parseFormula(champ, formulaPart);
            }
            return sum;
        }
    }
}
