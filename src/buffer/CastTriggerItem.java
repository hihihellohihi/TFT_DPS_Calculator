package buffer;

import champion.BuffableChampion;

import java.util.HashMap;

public class CastTriggerItem extends Item{

    /**
     * Constructor.
     *
     * @param champ
     * @param name
     * @param description
     */
    public CastTriggerItem(BuffableChampion champ, String name, HashMap<String, String> description) {
        super(champ, name, description);
    }

    public CastTriggerItem(BuffableChampion champ, String name, HashMap<String, String> description, int input) {
        super(champ, name, description, input);
    }

    @Override
    public void onHit() {
        if (trigger.equalsIgnoreCase("cast")) {
            if(champ.timeSinceCast <= duration) {
                this.triggerOn = 1;
            }
            else {
                this.triggerOn = 0;
            }
        }

        super.onHit();
    }

    @Override
    public void onCast() {
        if (trigger.equalsIgnoreCase("cast")) {
            this.triggerOn = 1;
        }
        super.onCast();
    }
}
