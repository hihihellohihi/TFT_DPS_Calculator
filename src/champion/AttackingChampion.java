package champion;

public abstract class AttackingChampion extends CastChampion{

    public DamageCounter counter = new DamageCounter();
    public float timeSinceStart = 0;
    /**
     * Constructor.
     */
    public AttackingChampion(float AD, float AP, float AS, float crit, float critDmg) {
        super(AD, AP, AS, crit, critDmg);
    }

    /**
     * Constructor.
     */
    public AttackingChampion(float AD, float AP, float AS, float crit, float critDmg,
                             float STADMult, float AOEADMult, float STAPMult, float AOEAPMult,
                             float mana, float manaMult, float currentMana) {
        super(AD, AP, AS, crit, critDmg, STADMult, AOEADMult, STAPMult, AOEAPMult, mana, manaMult, currentMana);
    }

    @Override
    public void attack() {
        float damage = baseNonCrit() * (1 + (stats.get("crit") * stats.get("critDmg")));
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
