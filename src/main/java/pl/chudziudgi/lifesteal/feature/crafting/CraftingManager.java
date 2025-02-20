package pl.chudziudgi.lifesteal.feature.crafting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.CraftingConfiguration;

import java.util.List;
import java.util.UUID;

public class CraftingManager {

    private final CraftingConfiguration craftingConfiguration;

    public CraftingManager(CraftingConfiguration craftingConfiguration) {
        this.craftingConfiguration = craftingConfiguration;
    }

    public void registerCraftings() {
        List<Crafting> craftings = craftingConfiguration.craftings;
        for (Crafting crafting : craftings) {
            registerCrafting(crafting);
        }
    }

    private void registerCrafting(Crafting crafting) {
        List<ItemStack> patternItems = crafting.getItemStacks();
        ItemStack result = crafting.getResult();

        if (patternItems.size() != 9) {
            throw new IllegalArgumentException("Crafting pattern must have exactly 9 items.");
        }

        NamespacedKey key = new NamespacedKey(JavaPlugin.getPlugin(SurvivalPlugin.class), String.valueOf(UUID.randomUUID()));

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("ABC", "DEF", "GHI");

        char[] slots = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
        for (int i = 0; i < 9; i++) {
            ItemStack item = patternItems.get(i);
            if (item != null && item.getType() != Material.AIR) {
                recipe.setIngredient(slots[i], item.getType());
            }
        }

        Bukkit.addRecipe(recipe);
    }
}

