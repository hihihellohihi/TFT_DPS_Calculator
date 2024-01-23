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

    public BuffableChampion buildChamp(HashMap<String, Float> c, String cTraits, int[] traitNumbers, int[] traitInputs, int targets) {
        BuffableChampion result;
        result = new BuffableChampion(c);
        result.stats.putIfAbsent("targets", (float) targets);
        String[] champTraits = cTraits.split(",");
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
