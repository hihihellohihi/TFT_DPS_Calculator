package champion;

import reader.ProcessChamps;
import reader.ProcessItems;
import reader.ProcessTraits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FindBIS {
    public static ArrayList<Object[]> FindAllBIS(String champName, int[] traitSizes, int[] traitStacks, int targets,
                                                 float time, ProcessTraits traitProcessor,
                                                 ProcessItems itemProcessor, ProcessChamps champProcessor) {
        Object[] allItems = itemProcessor.items.keySet().toArray();
        ItemizedChampionBuilder champBuilder = new ItemizedChampionBuilder(traitProcessor.traits,
                itemProcessor.items);
        int index1 = 0;
        int index2;
        int index3;
        HashMap<Float, String[]> damageNumbers = new HashMap<Float, String[]>();
        while (index1 < allItems.length) {
            index2 = index1;
            while(index2 < allItems.length) {
                index3 = index2;
                while (index3 < allItems.length) {
                    // clunky object to string due to toArray returning array of Obj
                    Object item1 = allItems[index1];
                    Object item2 = allItems[index2];
                    Object item3 = allItems[index3];
                    if (item1 instanceof String && item2 instanceof String && item3 instanceof String) {
                        String[] itemArray = new String[]{(String) item1, (String) item2,
                                (String) item3};
                        BuffableChampion currentChamp = champBuilder.buildChamp(
                                champProcessor.champs.get(champName),
                                traitSizes, traitStacks, targets, itemArray);
                        while (currentChamp.timeSinceStart < time) {
                            currentChamp.attack();
                        }
                        damageNumbers.put(currentChamp.counter.getDamage() / currentChamp.timeSinceStart,
                                itemArray);
                    }
                    index3 += 1;
                }
                index2 += 1;
            }
            index1 += 1;
        }
        Object[] sortedKeys = damageNumbers.keySet().toArray();
        Arrays.sort(sortedKeys);
        ArrayList<Object[]> results = new ArrayList<>();
        for (int i = sortedKeys.length - 10;i < sortedKeys.length; i++) {
            results.add(new Object[] {sortedKeys[i], damageNumbers.get(sortedKeys[i])});
        }
        return results;
    }
    public static ArrayList<Object[]> FindConditionalBIS(String champName, int[] traitSizes, int[] traitStacks,
                                                         int targets, float time) {
        ProcessTraits traitProcessor = new ProcessTraits();
        ProcessItems itemProcessor = new ProcessItems();
        ProcessChamps champProcessor = new ProcessChamps();
        try {
            traitProcessor.readTraitCSV("src/traits.csv");
            itemProcessor.readItemCSV("src/items.csv");
            champProcessor.readChampCSV("src/champions.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return FindAllBIS(champName, traitSizes, traitStacks, targets, time, traitProcessor,
                itemProcessor, champProcessor);
    }
    public static ArrayList<Object[]> FindUnconditionalBIS(String champName, int[] traitSizes, int[] traitStacks,
                                                         int targets, float time) {
        ProcessTraits traitProcessor = new ProcessTraits();
        ProcessItems itemProcessor = new ProcessItems();
        ProcessChamps champProcessor = new ProcessChamps();
        try {
            traitProcessor.readTraitCSV("src/traits.csv");
            itemProcessor.readItemCSV("src/items.csv");
            champProcessor.readChampCSV("src/champions.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        itemProcessor.items.remove("Guardbreaker");
        itemProcessor.items.remove("Giant Slayer");
        itemProcessor.items.remove("Blue Buff");
        return FindAllBIS(champName, traitSizes, traitStacks, targets, time, traitProcessor,
                itemProcessor, champProcessor);
    }
}
