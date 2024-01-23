package champion;

import buffer.Item;

import java.util.HashMap;

public class ItemizedChampionBuilder extends BuildChampionWithTraits {
    public HashMap<String, HashMap<String, String>> items = new HashMap<String, HashMap<String, String>>();
    public ItemizedChampionBuilder(HashMap<String, HashMap<String, String>> traits,
                                   HashMap<String, HashMap<String, String>> items) {
        super(traits);
        this.items = items;
    }


    public BuffableChampion buildChamp(HashMap<String, Float> c, String cTraits, int[] traitNumbers, int[] traitInputs, int targets,
                                       String[] items) {
        BuffableChampion result =  super.buildChamp(c, cTraits, traitNumbers, traitInputs, targets);
        for (String item: items) {
            Item newItem = new Item(result, item, this.items.get(item));
            result.allBuffers.add(newItem);
        }
        return result;
    }
}
