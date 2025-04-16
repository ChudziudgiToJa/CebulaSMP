package pl.chudziudgi.lifesteal.feature.villager;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;

public class VillagerController implements Listener {

    @EventHandler
    public void onVillagerAcquireTrade(VillagerAcquireTradeEvent event) {
        MerchantRecipe recipe = event.getRecipe();
        List<ItemStack> ingredients = recipe.getIngredients();
        List<ItemStack> newIngredients = new ArrayList<>();

        for (ItemStack item : ingredients) {
            if (item.getType() == Material.EMERALD) {
                ItemStack goldBlock = new ItemStack(Material.GOLD_BLOCK, item.getAmount());
                newIngredients.add(goldBlock);
            } else {
                newIngredients.add(item);
            }
        }

        MerchantRecipe newRecipe = new MerchantRecipe(
                recipe.getResult(),
                recipe.getUses(),
                recipe.getMaxUses(),
                recipe.hasExperienceReward(),
                recipe.getVillagerExperience(),
                recipe.getPriceMultiplier(),
                recipe.getDemand(),
                recipe.getSpecialPrice()
        );
        newRecipe.setIngredients(newIngredients);
        event.setRecipe(newRecipe);
    }

}
