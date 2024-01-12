package champion;

import java.util.HashMap;
import java.util.HashSet;
import buffer.IBuffer;

public class BuffableChampion extends CastingChampion{

    public HashMap<String, HashMap<Object, Float>> allBuffs = new HashMap<String, HashMap<Object, Float>>();

    public HashSet<IBuffer> allBuffers = new HashSet<IBuffer>();
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
    public BuffableChampion(float AD, float AP, float AS, float crit, float critDmg, int targets, float castTime) {
        super(AD, AP, AS, crit, critDmg, targets, castTime);
        allBuffs.put("AD", new HashMap<Object, Float>());
        allBuffs.put("AP", new HashMap<Object, Float>());
        allBuffs.put("AS", new HashMap<Object, Float>());
        //stupid workaround because buffs overwrite the initial stats
        allBuffs.putIfAbsent("crit", new HashMap<Object, Float>());
        allBuffs.get("crit").put(this, crit);
        allBuffs.putIfAbsent("critDmg", new HashMap<Object, Float>());
        allBuffs.get("critDmg").put(this, critDmg);
    }

    /**
     * Constructor.
     *
     * @param AD        base AD
     * @param AP        base AP (likely 100)
     * @param AS        base AS
     * @param crit      base crit
     * @param critDmg   base critDmg
     * @param STADMult  base AD mult that cannot hit AOE
     * @param AOEADMult base AD mult that can hit AOE
     * @param STAPMult  base AP mult that cannot hit AOE
     * @param AOEAPMult baAse AP mult that can hit AOE
     * @param mana      base mana cost
     * @param targets   number of targets being hit
     * @param castTime  cast time
     */
    public BuffableChampion(float AD, float AP, float AS, float crit, float critDmg, float STADMult,
                           float AOEADMult, float STAPMult, float AOEAPMult, float mana, float manaMult,
                           float currentMana, float targets, float castTime) {
        super(AD, AP, AS, crit, critDmg, STADMult, AOEADMult, STAPMult, AOEAPMult,
                mana, manaMult, currentMana, targets, castTime);
        //stupid workaround because buffs overwrite the initial stats
        allBuffs.putIfAbsent("crit", new HashMap<Object, Float>());
        allBuffs.get("crit").put(this, crit);
        allBuffs.putIfAbsent("critDmg", new HashMap<Object, Float>());
        allBuffs.get("critDmg").put(this, critDmg);
        allBuffs.putIfAbsent("mana", new HashMap<Object, Float>());
        allBuffs.get("mana").put(this, mana);
        allBuffs.putIfAbsent("currentMana", new HashMap<Object, Float>());
        allBuffs.get("currentMana").put(this, currentMana);
        allBuffs.putIfAbsent("manaMult", new HashMap<Object, Float>());
        allBuffs.get("manaMult").put(this, manaMult);
    }

    public void updateBuffs(String attribute) {
        float sum = 0;
        for (Float num: allBuffs.get(attribute).values()) {
            sum += num;
        }
        stats.put(attribute, sum);
    }

    /**
     * Changes the buff provided by a buffer object to a certain amount for the given attribute. Also
     * updates the champion to receive the buff.
     * @param buffer the object buffing the champion. Any given object can buff any attribute only once,
     *               but they can buff any number of attributes.
     * @param attribute the attribute being buffed.
     * @param amount the amount the attribute is buffed by.
     */
    public void buff(Object buffer, String attribute, float amount) {
        if (buffer instanceof IBuffer) {
            allBuffers.add((IBuffer) buffer);
        }
        allBuffs.putIfAbsent(attribute, new HashMap<Object, Float>());
        allBuffs.get(attribute).put(buffer, amount);
        updateBuffs(attribute);
    }

    @Override
    public void attack() {
        for (IBuffer buffer: allBuffers) {
            buffer.onHit();
        }
        super.attack();
    }

    @Override
    public void cast() {
        for (IBuffer buffer: allBuffers) {
            buffer.onCast();
        }
        super.cast();
    }
}
