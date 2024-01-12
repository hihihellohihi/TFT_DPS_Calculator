package buffer;

import buffer.Processors.FormulaProcessor;
import champion.BuffableChampion;

import java.util.HashMap;

public class Item implements IBuffer{
    BuffableChampion champ;
    String stats;
    int input = 0;
    int triggerOn = 0;
    String trigger;
    int duration = 0;
    int stacks = 0;
    String[] stacker;
    int stacksCap = 0;

    /**
     * Constructor.
     */
    public Item(BuffableChampion champ, String name, HashMap<String, String> description) {
        this.champ = champ;
        stats = description.get("Stats");
        trigger = description.get("y");
        if (!description.get("Duration").isEmpty()) {
            duration = Integer.parseInt(description.get("Duration"));
        }
        stacker = description.get("z").split(" ");
        if (!description.get("Stacks Cap").isEmpty()) {
            stacksCap = Integer.parseInt(description.get("Stacks Cap"));
        }
        //dumb workaround for no stack cap
        else {
            stacksCap = 1000;
        }
        // split by ; to get all stats, buff champ on creation of trait
        String[] allStats = stats.split(";");
        for (String stat: allStats) {
            // split by : to get the formula on the right and the attribute on the left
            String[] formulaAttribute = stat.split(":");
            champ.buff(this, formulaAttribute[0],
                    FormulaProcessor.parseFormula(this, formulaAttribute[1]));
        }
    }

    public Item(BuffableChampion champ, String name, HashMap<String, String> description, int input) {
        this(champ, name, description);
        this.input = input;
        //I'm dumb and I need to rebuff if input, currently a workaround
        String[] allStats = stats.split(";");
        for (String stat: allStats) {
            // split by : to get the formula on the right and the attribute on the left
            String[] formulaAttribute = stat.split(":");
            champ.buff(this, formulaAttribute[0],
                    FormulaProcessor.parseFormula(this, formulaAttribute[1]));
        }
    }

    /**
     * Stuff the trait does on hit
     */
    @Override
    public void onHit() {
        // save time if trait inactive / there is no reason to update anything
        if (trigger.isEmpty() && !stacker[0].equals("attack")) {
            return;
        }
        if (stacker[0].equals("attack") && stacks < stacksCap) {
            stacks += 1;
        }
        if (stacker[0].equals("time") && stacks < stacksCap) {
            stacks = (int) champ.timeSinceStart / Integer.parseInt(stacker[1]);
        }
        // split by ; to get all stats
        String[] allStats = stats.split(";");
        for (String stat: allStats) {
            // split by : to get the formula on the right and the attribute on the left
            String[] formulaAttribute = stat.split(":");
            champ.buff(this, formulaAttribute[0],
                    FormulaProcessor.parseFormula(this, formulaAttribute[1]));
            champ.updateBuffs(formulaAttribute[0]);
        }
    }

    /**
     * Stuff the trait does on cast
     */
    @Override
    public void onCast() {
        if (trigger.isEmpty() && !stacker[0].equals("cast")) {
            return;
        }
        if (stacker[0].equalsIgnoreCase("cast") && stacks < stacksCap) {
            stacks += 1;
        }
        // split by ; to get all stats
        String[] allStats = stats.split(";");
        for (String stat: allStats) {
            // split by : to get the formula on the left and the attribute on the right
            String[] formulaAttribute = stat.split(":");
            champ.buff(this, formulaAttribute[0],
                    FormulaProcessor.parseFormula(this, formulaAttribute[1]));
            champ.updateBuffs(formulaAttribute[0]);
        }
    }

    @Override
    public int getInput() {
        return input;
    }

    @Override
    public int getTriggerOn() {
        return triggerOn;
    }

    @Override
    public int getStacks() {
        return stacks;
    }
}
