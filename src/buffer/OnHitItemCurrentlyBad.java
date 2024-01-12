package buffer;

import champion.BuffableChampion;

import java.util.HashMap;

public class OnHitItemCurrentlyBad extends CastTriggerItem{
    /**
     * Constructor.
     *
     * @param champ
     * @param name
     * @param description
     */
    public OnHitItemCurrentlyBad(BuffableChampion champ, String name, HashMap<String, String> description) {
        super(champ, name, description);
    }

    public OnHitItemCurrentlyBad(BuffableChampion champ, String name, HashMap<String, String> description, int input) {
        super(champ, name, description, input);
    }

    @Override
    public void onHit() {
        if (trigger.equalsIgnoreCase("on-hit")) {
            if (champ.stats.get("on-hitDmg") != null) {
                champ.counter.dealDamage(champ.baseNonCrit() * champ.stats.get("on-hitDmg"));
            }
            else if (champ.stats.get("on-hitMana") != null) {
                champ.stats.put("currentMana", champ.stats.get("currentMana") + champ.stats.get("on-hitMana"));
            }
        }
        super.onHit();
    }
}
