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
     */
    public BuffableChampion(HashMap<String, Float> stats) {
        super(stats);
        //stupid workaround because buffs overwrite the initial stats
        allBuffs.putIfAbsent("crit", new HashMap<Object, Float>());
        allBuffs.get("crit").put(this, stats.get("crit"));
        allBuffs.putIfAbsent("critDmg", new HashMap<Object, Float>());
        allBuffs.get("critDmg").put(this, stats.get("critDmg"));
        if (stats.get("canCast") == 1) {
            allBuffs.putIfAbsent("mana", new HashMap<Object, Float>());
            allBuffs.get("mana").put(this, stats.get("mana"));
            allBuffs.putIfAbsent("currentMana", new HashMap<Object, Float>());
            allBuffs.get("currentMana").put(this, stats.get("currentMana"));
            allBuffs.putIfAbsent("manaMult", new HashMap<Object, Float>());
            allBuffs.get("manaMult").put(this, stats.get("manaMult"));
        }
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
