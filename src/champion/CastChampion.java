package champion;

public abstract class CastChampion extends StatChampion{

    /**
     * Constructor.
     */
    public CastChampion(float AD, float AP, float AS, float crit, float critDmg) {
        super(AD, AP, AS, crit, critDmg);
        this.stats.put("STADMult", 0f);
        this.stats.put("AOEADMult", 0f);
        this.stats.put("STAPMult", 0f);
        this.stats.put("AOEAPMult", 0f);
        this.stats.put("mana", 10f);
        this.stats.put("manaMult", 1f);
        this.stats.put("currentMana", 0f);
    }

    /**
     * Constructor.
     */
    public CastChampion(float AD, float AP, float AS, float crit, float critDmg, float STADMult, float AOEADMult, float STAPMult, float AOEAPMult, float mana, float manaMult, float currentMana) {
        super(AD, AP, AS, crit, critDmg);
        this.stats.put("STADMult", STADMult);
        this.stats.put("AOEADMult", AOEADMult);
        this.stats.put("STAPMult", STAPMult);
        this.stats.put("AOEAPMult", AOEAPMult);
        this.stats.put("mana", mana);
        this.stats.put("manaMult", manaMult);
        this.stats.put("currentMana", currentMana);
    }
}
