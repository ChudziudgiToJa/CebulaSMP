package pl.chudziudgi.lifesteal.feature.crafting;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.List;

@Getter
public class Crafting implements Serializable {
    private final List<ItemStack> itemStacks;
    private final ItemStack result;

    public Crafting(List<ItemStack> itemStacks, ItemStack result) {
        this.itemStacks = itemStacks;
        this.result = result;
    }
}
