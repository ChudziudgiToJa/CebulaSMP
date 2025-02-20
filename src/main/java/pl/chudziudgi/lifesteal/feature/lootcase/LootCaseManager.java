package pl.chudziudgi.lifesteal.feature.lootcase;

import java.util.List;
import java.util.Random;

public class LootCaseManager {

    public static LootCaseChance pickRandomItem(List<LootCaseChance> items) {
        double totalChance = items.stream().mapToDouble(LootCaseChance::getChance).sum();

        double randomValue = new Random().nextDouble() * totalChance;

        double cumulativeChance = 0.0;
        for (LootCaseChance item : items) {
            cumulativeChance += item.getChance();
            if (randomValue <= cumulativeChance) {
                return item;
            }
        }
        throw new IllegalStateException("Nie udało się wylosować żadnego przedmiotu.");
    }
}
