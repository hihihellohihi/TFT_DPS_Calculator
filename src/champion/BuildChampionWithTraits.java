package champion;

import buffer.CastTriggerTrait;
import buffer.Trait;

import java.util.HashMap;
import java.util.Objects;

public class BuildChampionWithTraits {

    public HashMap<String, HashMap<String, String>> traits;

    public BuildChampionWithTraits(HashMap<String, HashMap<String, String>> traits) {
        this.traits = traits;
    }

    public BuffableChampion buildChamp(HashMap<String, String> c, int[] traitNumbers, int[] traitInputs, int targets) {
        BuffableChampion result;
        if (Objects.equals(c.get("canCast"), "y")) {
            result = new BuffableChampion(Float.parseFloat(c.get("AD")),
                    Float.parseFloat(c.get("AP")), Float.parseFloat(c.get("AS")),
                    Float.parseFloat(c.get("crit")), Float.parseFloat(c.get("critDmg")),
                    Float.parseFloat(c.get("STADMult")), Float.parseFloat(c.get("AOEADMult")),
                    Float.parseFloat(c.get("STAPMult")), Float.parseFloat(c.get("AOEAPMult")),
                    Float.parseFloat(c.get("mana")), Float.parseFloat(c.get("manaMult")),
                    Float.parseFloat(c.get("currentMana")), targets, Float.parseFloat(c.get("castTime")));
        }
        else {
            result = new BuffableChampion(Float.parseFloat(c.get("AD")),
                    Float.parseFloat(c.get("AP")), Float.parseFloat(c.get("AS")),
                    Float.parseFloat(c.get("crit")), Float.parseFloat(c.get("critDmg")),
                    targets, Float.parseFloat(c.get("castTime")));
        }
        String[] champTraits = c.get("Traits").split(",");
        for (int i = 0; i < champTraits.length; i++) {
            int traitNum;
            int traitInput;
            if (i < traitNumbers.length) {
                traitNum = traitNumbers[i];
            }
            else {
                traitNum = 0;
            }
            if (i < traitInputs.length) {
                traitInput = traitInputs[i];
            }
            else {
                traitInput = 0;
            }
            Trait newTrait = new CastTriggerTrait(result, "KDA", traits.get(champTraits[i]), traitNum, traitInput);
            result.allBuffers.add(newTrait);
        }
        return result;
    }
}
