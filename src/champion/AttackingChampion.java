package champion;

import java.util.HashMap;

public abstract class AttackingChampion extends StatChampion{

    public DamageCounter counter = new DamageCounter();
    public float timeSinceStart = 0;
    /**
     * Constructor.
     */
    public AttackingChampion(HashMap<String, Float> stats) {
        super(stats);
    }

    @Override
    public void attack() {
        float damage;
        if (stats.get("crit") > 1) {
            damage = baseNonCrit() * (1 + stats.get("critDmg") + (stats.get("crit") - 1) / 2);
        }
        else {
            damage = baseNonCrit() * (1 + (stats.get("crit") * stats.get("critDmg")));
        }
        if (stats.get("on-hitDmg") != null) {
                damage += baseNonCrit() * stats.get("on-hitDmg");
            }
        counter.dealDamage(damage);
        this.timeSinceStart += (float) Math.max(1/(stats.get("baseAS") * (1 + stats.get("ASMult"))), 0.2);
    }

    public float baseNonCrit() {
        return (stats.get("baseAD") * (1 + stats.get("ADMult"))) * (1 + stats.get("DMG"));
    }
}
