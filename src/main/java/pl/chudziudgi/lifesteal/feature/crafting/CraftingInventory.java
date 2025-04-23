package pl.chudziudgi.lifesteal.feature.crafting;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.CraftingConfiguration;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.SimpleInventory;

import java.util.List;

public class CraftingInventory {

    private final SurvivalPlugin survivalPlugin;
    private final CraftingConfiguration craftingConfiguration;

    public CraftingInventory(SurvivalPlugin survivalPlugin, CraftingConfiguration craftingConfiguration) {
        this.survivalPlugin = survivalPlugin;
        this.craftingConfiguration = craftingConfiguration;
    }

    public void showItems(final Player player) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 9 * 6, "&fcraftingi:");
        Inventory inventory = simpleInventory.getInventory();

        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53, 49
        };

        for (int slot : glassBlueSlots) {
            inventory.setItem(slot, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                    .setName(" ")
                    .build());
        }

        for (Crafting crafting : this.craftingConfiguration.craftings) {
            inventory.addItem(new ItemBuilder(crafting.getResult().getType())
                            .setName(crafting.getResult().getItemMeta().getItemName())
                    .addLore("", "&akliknij aby otworzyć.")
                    .build());
        }

        simpleInventory.click(event -> {
            event.setCancelled(true);

            if (event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)) return;

            for (Crafting crafting : this.craftingConfiguration.craftings) {
                if (event.getCurrentItem().getType() == crafting.getResult().getType()) {
                    showCrafting(player, crafting);
                    player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 5, 5);
                    return;
                }
            }
        });

        player.playSound(player.getLocation(), Sound.BLOCK_BARREL_OPEN, 5, 5);
        player.openInventory(inventory);
    }



    public void showCrafting(final Player player, final Crafting crafting) {
        SimpleInventory simpleInventory = new SimpleInventory(
                this.survivalPlugin,
                InventoryType.WORKBENCH,
                "&fCrafting"
        );
        Inventory inventory = simpleInventory.getInventory();

        inventory.setItem(0, crafting.getResult());

        List<ItemStack> itemStacks = crafting.getItemStacks();
        for (int i = 0; i < Math.min(9, itemStacks.size()); i++) {
            inventory.setItem(i + 1, itemStacks.get(i));
        }

        simpleInventory.click(event -> {
            event.setCancelled(true);
            showItems(player);
        });
        player.playSound(player.getLocation(), Sound.BLOCK_BARREL_OPEN, 5.0f, 5.0f);
        player.openInventory(inventory);
    }


}
