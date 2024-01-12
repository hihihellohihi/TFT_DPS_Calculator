package buffer.Processors;

import buffer.IBuffer;

/**
 * Processes formulas in the specific format used for the csv files.
 */
public class FormulaProcessor {
    public static float parseFormula(IBuffer buffer, String formula) {
        // split by + since it is the lowest in order of operations
        String[] plusFormula = formula.split("\\+");
        if (plusFormula.length == 1) {
            // then split by * if there is no +
            String[] timesFormula = formula.split("\\*");
            // if it's just a single term that can be processed either as a num or a var
            if (timesFormula.length == 1) {
                if (plusFormula[0].equalsIgnoreCase("x")) {
                    return buffer.getInput();
                }
                else if (plusFormula[0].equalsIgnoreCase("y")) {
                    return buffer.getTriggerOn();
                }
                else if (plusFormula[0].equalsIgnoreCase("z")) {
                    return buffer.getStacks();
                }
                else {
                    return Float.parseFloat(plusFormula[0]);
                }
            }
            else {
                float product = 1;
                for (String formulaPart: timesFormula) {
                    product *= FormulaProcessor.parseFormula(buffer, formulaPart);
                }
                return product;
            }
        }
        else {
            float sum = 0;
            for (String formulaPart: plusFormula) {
                sum += FormulaProcessor.parseFormula(buffer, formulaPart);
            }
            return sum;
        }
    }

}
