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
    public StatChampion(HashMap<String, Float> stats) {
        this.stats = stats;
    }

}
