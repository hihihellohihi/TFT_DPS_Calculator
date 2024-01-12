package buffer;

import champion.BuffableChampion;

import java.util.HashMap;

public class CastTriggerTrait extends Trait{
    /**
     * Constructor.
     *
     * @param champ
     * @param name
     * @param description
     * @param number
     */
    public CastTriggerTrait(BuffableChampion champ, String name, HashMap<String, String> description, int number) {
        super(champ, name, description, number);
    }

    public CastTriggerTrait(BuffableChampion champ, String name, HashMap<String, String> description, int number, int input) {
        super(champ, name, description, number, input);
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
