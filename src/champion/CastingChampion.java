package champion;

public class CastingChampion extends AttackingChampion{

    public float timeSinceCast = 1000;
    /**
     * Constructor.
     *
     * @param AD
     * @param AP
     * @param AS
     * @param crit
     * @param critDmg
     * @param targets
     * @param castTime
     */
    public CastingChampion(float AD, float AP, float AS, float crit, float critDmg, int targets, float castTime) {
        super(AD, AP, AS, crit, critDmg);
        this.stats.put("targets", (float) targets);
        this.stats.put("castCrit", 0f);
        this.stats.put("castTime", castTime);
        this.stats.put("numCasts", 0f);
    }

    /**
     * Constructor.
     *
     * @param AD base AD
     * @param AP base AP (likely 100)
     * @param AS base AS
     * @param crit base crit
     * @param critDmg base critDmg
     * @param STADMult base AD mult that cannot hit AOE
     * @param AOEADMult base AD mult that can hit AOE
     * @param STAPMult base AP mult that cannot hit AOE
     * @param AOEAPMult base AP mult that can hit AOE
     * @param mana base mana cost
     * @param targets number of targets being hit
     * @param castTime cast time
     */
    public CastingChampion(float AD, float AP, float AS, float crit, float critDmg, float STADMult,
                           float AOEADMult, float STAPMult, float AOEAPMult, float mana, float manaMult,
                           float currentMana, float targets, float castTime) {
        super(AD, AP, AS, crit, critDmg, STADMult, AOEADMult, STAPMult, AOEAPMult, mana, manaMult, currentMana);
        this.stats.put("targets", (float) targets);
        this.stats.put("castCrit", 0f);
        this.stats.put("castTime", castTime);
        this.stats.put("numCasts", 0f);
    }

    @Override
    public void attack() {
        // update cast stuff BEFORE attacking
        // this is because for some reason, this is how it seems to work in game
        // ex. corki gets big shot buff on the attack that gives him full mana??
        this.timeSinceCast += 1/(stats.get("baseAS") * (1 + stats.get("ASMult")));
        stats.put("currentMana", stats.get("currentMana") + 10);
        if (stats.get("on-hitMana") != null) {
                stats.put("currentMana", stats.get("currentMana") + stats.get("on-hitMana"));
        }
        if (stats.get("currentMana") >= stats.get("mana")) {
            cast();
            stats.put("currentMana", 0f);
            this.timeSinceCast = 0;
        }
        super.attack();
    }

    @Override
    public void cast() {
        stats.put("numCasts", stats.get("numCasts") + 1);
        float ADdamage = baseNonCrit();
        if (stats.get("castCrit") != 0) {
            if (stats.get("crit") > 1) {
                ADdamage = baseNonCrit() * (1 + stats.get("critDmg") + (stats.get("crit") - 1) / 2);
            }
            else {
                ADdamage = baseNonCrit() * (1 + (stats.get("crit") * stats.get("critDmg")));
            }
        }
        counter.dealDamage(ADdamage * stats.get("STADMult") + ADdamage * stats.get("AOEADMult")
                * stats.get("targets"));
        float APdamage = (stats.get("baseAP") * (1 + stats.get("APMult"))) * (1 + stats.get("crit")
                * stats.get("critDmg"));
        if (stats.get("castCrit") != 0) {
            if (stats.get("crit") > 1) {
                APdamage = baseNonCrit() * (1 + stats.get("critDmg") + (stats.get("crit") - 1) / 2);
            }
            else {
                APdamage = baseNonCrit() * (1 + (stats.get("crit") * stats.get("critDmg")));
            }
        }
        counter.dealDamage(APdamage * stats.get("STAPMult") + APdamage * stats.get("AOEAPMult")
                * stats.get("targets"));
        timeSinceStart += stats.get("castTime");
    }
}
