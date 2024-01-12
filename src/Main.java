import champion.*;
import reader.ProcessChamps;
import reader.ProcessItems;
import reader.ProcessTraits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        ProcessTraits traitProcessor = new ProcessTraits();
        ProcessItems itemProcessor = new ProcessItems();
        ProcessChamps champProcessor = new ProcessChamps();
        try {
            traitProcessor.readTraitCSV("src/traits.csv");
            itemProcessor.readItemCSV("src/items.csv");
            champProcessor.readChampCSV("src/champions.csv");
            System.out.println(traitProcessor.traits);
            System.out.println(itemProcessor.items);
            System.out.println(champProcessor.champs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        BuffableChampion corki = new BuffableChampion(108f, 100f, 0.75f, 0.25f,
//                0.4f, 0f, 3.2f, 0f, 0f, 60f,
//                1f, 0f, 1, 0f);
//        BuffableChampion someone = new BuffableChampion(100f, 100f, 0.75f, 1f,
//                0.4f, 0f, 0f, 0f, 0f, 10f,
//                1f, 0f, 1, 0f);
//        corki.buff(corki, "ADMult", 0.4f);
//        //corki.buff(corki, "ADMult", -0.1f);
//        //Trait KDA = new Trait(corki, "KDA", traitProcessor.traits.get("KDA"), 10);
//        //corki.allBuffers.add(KDA);\
//        Trait Big_Shot = new CastTriggerTrait(corki, "Big Shot", traitProcessor.traits.get("Big Shot"), 2);
//        //Trait Eight_Bit = new Trait(corki, "8-Bit", traitProcessor.traits.get("8-Bit"), 2, 10);
//        corki.allBuffers.add(Big_Shot);
//        //corki.allBuffers.add(Eight_Bit);
//        Item Runaan = new OnHitItem(someone, "Runaan", itemProcessor.items.get("Runaan's Hurricane"));
//        someone.allBuffers.add(Runaan);
//        someone.buff(someone, "ADMult", -0.25f);
        BuildChampionWithTraits champBuilder = new BuildChampionWithTraits(traitProcessor.traits);
        ItemizedChampionBuilder champBuilder2 = new ItemizedChampionBuilder(traitProcessor.traits, itemProcessor.items);
        BuffableChampion corki2 = champBuilder2.buildChamp(champProcessor.champs.get("Corki 3"),
                new int[] {2, 2}, new int[] {10}, 1, new String[] {"Red Buff"});
        int attackCounter = 0;
        while (corki2.timeSinceStart < 30) {
            attackCounter ++;
            corki2.attack();
        }
        System.out.println(attackCounter);
        System.out.println(corki2.stats.get("numCasts"));
//        while (someone.timeSinceStart < 16) {
//            someone.attack();
//        }
        System.out.println(corki2.counter.getDamage() / corki2.timeSinceStart);
        ArrayList<Object[]> BIS = FindBIS.FindConditionalBIS("Caitlyn 2", new int[] {2, 2},
                new int[] {10}, 2, 15);
        for (Object[] itemSet: BIS) {
            System.out.print(itemSet[0]);
            System.out.print(" ");
            if (itemSet[1] instanceof String[]) {
                System.out.println(Arrays.toString((String[]) itemSet[1]));
            }
        }
        System.out.println();
        ArrayList<Object[]> BIS2 = FindBIS.FindUnconditionalBIS("Ezreal 2", new int[] {2, 2},
                new int[] {10}, 2, 15);
        for (Object[] itemSet: BIS2) {
            System.out.print(itemSet[0]);
            System.out.print(" ");
            if (itemSet[1] instanceof String[]) {
                System.out.println(Arrays.toString((String[]) itemSet[1]));
            }
        }
    }
}