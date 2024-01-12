package champion;

import java.util.HashMap;

/**
 * Champion class with stats that every champion has
 */
public abstract class StatChampion implements IChampion {

    public HashMap<String, Float> stats = new HashMap<String, Float>();

    /**
     * Constructor.
     */
    public StatChampion(float AD, float AP, float AS, float crit, float critDmg) {
        stats.put("baseAD", AD);
        stats.put("baseAP", AP);
        stats.put("baseAS", AS);
        stats.put("ADMult", 0f);
        stats.put("APMult", 0f);
        stats.put("ASMult", 0f);
        stats.put("crit", crit);
        stats.put("critDmg", critDmg);
        stats.put("DMG", 0f);
    }

}
