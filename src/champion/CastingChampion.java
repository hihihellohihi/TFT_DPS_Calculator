package champion;

import java.util.HashMap;

public class CastingChampion extends AttackingChampion{

    public float timeSinceCast = 1000;
    /**
     * Constructor.
     */
    public CastingChampion(HashMap<String, Float> stats) {
        super(stats);
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
        stats.putIfAbsent("numCasts", 0f);
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
        if (stats.containsKey("triggerCast2")) {
            if (stats.get("numCasts") % stats.get("triggerCast2") == 0) {
                counter.dealDamage(ADdamage * stats.get("STADMult2") + ADdamage * stats.get("AOEADMult2")
                        * stats.get("targets"));
                counter.dealDamage(APdamage * stats.get("STAPMult2") + APdamage * stats.get("AOEAPMult2")
                        * stats.get("targets"));
            }
        }
        else {
            counter.dealDamage(ADdamage * stats.get("STADMult") + ADdamage * stats.get("AOEADMult")
                    * stats.get("targets"));
            counter.dealDamage(APdamage * stats.get("STAPMult") + APdamage * stats.get("AOEAPMult")
                    * stats.get("targets"));
        }
        if (stats.get("castTime") == 'x') {
            timeSinceStart += 1/(stats.get("baseAS") * (1 + stats.get("ASMult")));
        }
        else {
            timeSinceStart += stats.get("castTime");
        }
    }
}
