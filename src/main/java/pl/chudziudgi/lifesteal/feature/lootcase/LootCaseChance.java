package pl.chudziudgi.lifesteal.feature.lootcase;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@Getter
public class LootCaseChance implements Serializable {
    private final String itemStackInString;
    private final double chance;

    public LootCaseChance(String itemStackInString, double chance) {
        this.itemStackInString = itemStackInString;
        this.chance = chance;
    }
}
